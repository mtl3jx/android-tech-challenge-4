package captechventures.com.techchallenge4.cookbook.recipes;

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
import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 10/30/17.
 */

class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.RecipeViewHolder> {

    private final Context context;
    private List<Recipe> recipeList;
    private RecipeItemListener recipeItemListener;

    // tag for logging purposes
    private static final String TAG = RecipeItemAdapter.class.getSimpleName();

    public RecipeItemAdapter(Context context, List<Recipe> recipeList, RecipeItemListener recipeItemListener) {
        this.context = context;
        this.recipeList = recipeList;
        this.recipeItemListener = recipeItemListener;
    }

    @Override
    public RecipeItemAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe,parent, false);
        return new RecipeItemAdapter.RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecipeItemAdapter.RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
//        Log.d(TAG, position + " " + recipe);

        TableRow tableRow = holder.tableRow;

        // format cooktime
        Integer t = recipe.getMinutes();
        Integer hours = t / 60;
        Integer minutes = t % 60;

        StringBuilder cooktime = new StringBuilder();
        if (hours > 1) {
            cooktime.append(hours + " hrs");
        } else if (hours > 0) {
            cooktime.append(hours + " hr");
        }
        if (cooktime.length() > 0) cooktime.append(" ");
        if (minutes > 1) {
            cooktime.append(minutes + " mins");
        } else if (minutes > 0) {
            cooktime.append(minutes + "min");
        }

        // set all the values of the row
        holder.name.setText(recipe.getName());
        holder.cooktime.setText(cooktime.toString());

        // TODO: make UI changes: color, etc.

        if (position % 2 != 0) { // alternating row background colors
            tableRow.setBackgroundColor(ContextCompat.getColor(context, R.color.rowBackground));
        }

        // make row clickable
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos = holder.getAdapterPosition();
                Log.d(TAG, "Recipe " + recipeList.get(pos).getName() + " at " + pos + " clicked");

                // TODO: render RecipeDetails based on which ones clicked
                recipeItemListener.recipeItemClicked(recipeList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateList(List<Recipe> recipeList) {
        // diffUtil --> makes notify data set changes position specific and faster
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RecipeDiffCallback(this.recipeList, recipeList));

        Log.d(TAG, "Using DiffUtil to calculate position changes");

        // update categoryList
        this.recipeList = recipeList;

        // make changes to view
        diffResult.dispatchUpdatesTo(this);
    }

    // holds the view for each Recipe's TableRow
    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TableRow tableRow;
        public TextView name, cooktime;

        // view holder constructor
        public RecipeViewHolder(View view) {
            super(view);
            tableRow = view.findViewById(R.id.recipe_table_row);
            name = tableRow.findViewById(R.id.recipe_name);
            cooktime = tableRow.findViewById(R.id.recipe_cooktime);
        }
    }

    // one interface for adapter --> fragment
    public interface RecipeItemListener {
        // fragment methods the adapter wants to call go here
        void recipeItemClicked(Recipe recipe);
    }

}
