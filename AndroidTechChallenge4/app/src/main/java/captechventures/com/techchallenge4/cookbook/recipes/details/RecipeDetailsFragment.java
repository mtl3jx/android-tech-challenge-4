package captechventures.com.techchallenge4.cookbook.recipes.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.cookbook.recipes.RecipeSelectionFragment;
import captechventures.com.techchallenge4.database.CookbookDatabase;
import captechventures.com.techchallenge4.model.Category;
import captechventures.com.techchallenge4.model.Recipe;

public class RecipeDetailsFragment extends Fragment {

    private Context context;
    private Integer recipeId;
    private Recipe recipe;
    private RecipeDetailsListener listener;

    // layout vars
    private TextView name, category, cooktime, ingredients, instructions;
    private Button button_edit, button_delete;

    private static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";

    // tag for logging purposes
    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance(Integer recipeId) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID, recipeId);
//        Log.d(TAG, "Putting " + recipeId + " in global var recipeId");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);

        context = getActivity();
        if (context instanceof RecipeSelectionFragment.RecipeSelectionListener) {
            listener = (RecipeDetailsListener) context;
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            recipeId = bundle.getInt(KEY_RECIPE_ID);
//            Log.d(TAG, "Setting recipeId to: " + recipeId);
        } else {
            recipeId = null;
//            Log.e(TAG, "no recipeId found, setting to null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        // init all global layout vars
        name = view.findViewById(R.id.recipe_details_name);
        category = view.findViewById(R.id.recipe_details_category);
        cooktime = view.findViewById(R.id.recipe_details_cooktime);
        ingredients = view.findViewById(R.id.recipe_details_ingredients);
        instructions = view.findViewById(R.id.recipe_details_instructions);
        button_edit = view.findViewById(R.id.button_edit_recipe);
        button_delete = view.findViewById(R.id.button_delete_recipe);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (recipeId == null) Log.e(TAG, "No recipe ID found");

        // make DB call to find Recipe with ID recipeId and render (within Async task)
        recipe = CookbookDatabase.getInstance(getContext()).recipeDao().getById(recipeId);
        Log.d(TAG, "recipe details for #" + recipe.getId());

        // update toolbar title on mobile phone
        if (!listener.isTablet()) listener.updateToolbarTitle(recipe.getName());

        // change recipe details UI
        updateRecipeDetails(recipe);

    }

    public void updateRecipeDetails(Recipe recipe) {
        // get category name
        Category categoryName = CookbookDatabase.getInstance(getContext()).categoryDao().getCategory(recipe.getCategory());

        // format cooktime
        Integer t = recipe.getMinutes();
        Integer hours = t / 60;
        Integer minutes = t % 60;

        StringBuilder formattedTime = new StringBuilder();
        if (hours > 1) {
            formattedTime.append(hours + " hrs");
        } else if (hours > 0) {
            formattedTime.append(hours + " hr");
        }
        if (formattedTime.length() > 0) formattedTime.append(" ");
        if (minutes > 1) {
            formattedTime.append(minutes + " mins");
        } else if (minutes > 0) {
            formattedTime.append(minutes + "min");
        }

        // update layout vars
        if (listener.isTablet()) {
            name.setText(recipe.getName());
        }
        category.setText(categoryName.getCategory());
        cooktime.setText(formattedTime.toString());
        ingredients.setText(recipe.getIngredients());
        instructions.setText(recipe.getInstructions());

        // add onClickListener to category to link to selection of recipes from that category
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.categoryNameClicked(categoryName);
            }
        });

        // add onClickListener to edit button
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Edit button clicked");
                listener.editRecipe(recipe);
            }
        });

        // add onClickListener to delete button
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Delete button clicked");
                listener.deleteRecipe(recipe);
            }
        });
    }

    public interface RecipeDetailsListener {
        // methods that this fragment might want to call from activity
        void openCategorySelection();
        void categoryNameClicked(Category category);
        void updateToolbarTitle(String title);
        void editRecipe(Recipe recipe);
        boolean isTablet();
        void deleteRecipe(Recipe recipe);
    }
}
