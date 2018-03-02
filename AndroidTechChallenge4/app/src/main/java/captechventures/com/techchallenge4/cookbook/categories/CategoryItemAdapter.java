package captechventures.com.techchallenge4.cookbook.categories;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.model.Category;

/**
 * Created by mluansing on 10/27/17.
 */

class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryViewHolder> {

    private final Context context;
    private List<Category> categoryList;
    private CategoryItemListener categoryItemListener;

    // tag for logging purposes
    private static final String TAG = CategoryItemAdapter.class.getSimpleName();

    public CategoryItemAdapter(Context context, List<Category> categoryList, CategoryItemListener categoryItemListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.categoryItemListener = categoryItemListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
        final Category category = categoryList.get(position);
//        Log.d(TAG, position + " " + category);

        TableRow tableRow = holder.tableRow;

        // set all the values of the row
        holder.name.setText(category.getCategory());

        // TODO: make any UI changes: color, etc.

        if (position % 2 != 0) { // alternating row background colors
            tableRow.setBackgroundColor(ContextCompat.getColor(context, R.color.rowBackground));
        }

        // make row clickable
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos = holder.getAdapterPosition();
                Log.d(TAG, "Category " + categoryList.get(pos).getCategory() + " at " + pos + " clicked");

                // render RecipeSelectionFragment based on which category was clicked
                categoryItemListener.categoryItemClicked(categoryList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateList(List<Category> categoryList) {

        // diffUtil --> makes notify data set changes position specific and faster
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CategoryDiffCallback(this.categoryList, categoryList));

//        Log.d(TAG, "Using DiffUtil to calculate position changes");

        // update categoryList
        this.categoryList = categoryList;

        // make changes to view
        diffResult.dispatchUpdatesTo(this);
    }

    // holds the view for each Category's TableRow
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TableRow tableRow;
        public TextView name;

        // view holder constructor
        public CategoryViewHolder(View view) {
            super(view);
            tableRow = view.findViewById(R.id.category_table_row);
            name = tableRow.findViewById(R.id.category_name);
        }
    }

    // one interface for adapter --> fragment
    public interface CategoryItemListener {
        // fragment methods the adapter wants to call go here
        void categoryItemClicked(Category category);
    }

}
