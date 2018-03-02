package captechventures.com.techchallenge4.cookbook.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.database.CookbookDatabase;
import captechventures.com.techchallenge4.model.Recipe;


public class RecipeSelectionFragment extends Fragment
    implements RecipeItemAdapter.RecipeItemListener {

    // global vars
    private Integer categoryId;
    private List<Recipe> recipeList;
    private RecipeSelectionListener listener;

    // layout vars
    private TextView message;
    private RecyclerView recipeRecyclerView;
    private RecipeItemAdapter recipeItemAdapter;

    private static final String KEY_CATEGORY_ID = "KEY_CATEGORY_ID";

    // tag for logging purposes
    private static final String TAG = RecipeSelectionFragment.class.getSimpleName();

    public RecipeSelectionFragment() {
        // Required empty public constructor
    }

    // to pass in arguments to new instance
    public static RecipeSelectionFragment newInstance(int categoryId) {
        RecipeSelectionFragment fragment = new RecipeSelectionFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_selection, container, false);

        if (getActivity() instanceof RecipeSelectionListener) {
            listener = (RecipeSelectionListener) getActivity();
        }

        message = rootView.findViewById(R.id.msg_no_recipes);
        recipeRecyclerView = rootView.findViewById(R.id.recipe_recycler_view);
        recipeItemAdapter = new RecipeItemAdapter(getContext(), new ArrayList<>(), this);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeRecyclerView.setAdapter(recipeItemAdapter);

        // unpack bundle and store Category as instance variable
        if (getArguments() != null) {
            categoryId = getArguments().getInt(KEY_CATEGORY_ID);
        } else {
            categoryId = null;
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load list of categories from DB based on categoryId
        recipeList = CookbookDatabase.getInstance(getContext()).recipeDao().getByCategory(categoryId);
        Log.d(TAG, "recipeList: " + recipeList.size());

        // show msg if none found
        if (recipeList.size() <= 0) {
            recipeRecyclerView.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
            recipeRecyclerView.setVisibility(View.VISIBLE);
        }

        // update toolbar title
        String toolbarTitle = CookbookDatabase.getInstance(getContext()).categoryDao().getCategory(categoryId).getCategory();
        listener.updateToolbarTitle(toolbarTitle);

        // update adapter
        recipeItemAdapter.updateList(recipeList);

    }

    @Override
    public void recipeItemClicked(Recipe recipe) {
        listener.openRecipeDetails(recipe.getId());
    }

    public interface RecipeSelectionListener {
        // methods that this fragment might want to call from CookbookActivity
        void openRecipeDetails(int recipeId);
        void updateToolbarTitle(String title);
    }
}
