package captechventures.com.techchallenge4.cookbook.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.database.CookbookDatabase;
import captechventures.com.techchallenge4.model.Category;

public class CategorySelectionFragment extends Fragment
        implements CategoryItemAdapter.CategoryItemListener {

    private List<Category> categoryList;
    private TextView message;
    private RecyclerView categoryRecyclerView;
    private CategoryItemAdapter categoryItemAdapter;
    private CategorySelectionListener listener;

    // tag for logging purposes
    private static final String TAG = CategorySelectionFragment.class.getSimpleName();

    public CategorySelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof CategorySelectionListener) {
            listener = (CategorySelectionListener) getActivity();
        }

        View rootView = inflater.inflate(R.layout.fragment_category_selection, container, false);

        message = rootView.findViewById(R.id.msg_no_categories);
        categoryRecyclerView = rootView.findViewById(R.id.category_recycler_view);
        categoryItemAdapter = new CategoryItemAdapter(getContext(), new ArrayList<>(), this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryRecyclerView.setAdapter(categoryItemAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load list of categories from DB
        categoryList = CookbookDatabase.getInstance(getContext()).categoryDao().getAll();

        // show msg if none found
        if (categoryList.size() <= 0) {
            categoryRecyclerView.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
        }

        // update toolbar title
        listener.updateToolbarTitle("How Hungry Are You?");

        // update list in adapter
        categoryItemAdapter.updateList(categoryList);

    }

    @Override
    public void categoryItemClicked(Category category) {
        listener.openRecipesInCategory(category.getId());
    }

    public interface CategorySelectionListener {
        // methods that this fragment might want to call from CookbookActivity (etc.) go here
        void openRecipesInCategory(int categoryId);
        void updateToolbarTitle(String title);
    }
}
