package captechventures.com.techchallenge4.cookbook;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.cookbook.categories.CategorySelectionFragment;
import captechventures.com.techchallenge4.cookbook.recipes.RecipeSelectionFragment;
import captechventures.com.techchallenge4.cookbook.recipes.details.RecipeDetailsFragment;
import captechventures.com.techchallenge4.cookbook.recipes.details.create.RecipeCreationDialogFragment;
import captechventures.com.techchallenge4.cookbook.recipes.details.delete.RecipeDeletionDialogFragment;
import captechventures.com.techchallenge4.cookbook.recipes.details.edit.RecipeModificationDialogFragment;
import captechventures.com.techchallenge4.model.Category;
import captechventures.com.techchallenge4.model.Recipe;

public class CookbookActivity extends AppCompatActivity
    implements CategorySelectionFragment.CategorySelectionListener,
        RecipeSelectionFragment.RecipeSelectionListener,
        RecipeDetailsFragment.RecipeDetailsListener,
        RecipeCreationDialogFragment.RecipeCreationListener,
        RecipeModificationDialogFragment.RecipeModificationListener,
        RecipeDeletionDialogFragment.RecipeDeletionListener {

    // layout vars
    Toolbar toolbar;
    FloatingActionButton button_new_recipe;
    RecipeCreationDialogFragment recipeCreation;
    RecipeModificationDialogFragment recipeModification;
    RecipeDeletionDialogFragment recipeDeletion;

    // saving fragment instance
    private static final String KEY_TOOLBAR_TITLE = "KEY_TOOLBAR_TITLE",
            FRAGMENT_CATEGORY_SELECTION = "FRAGMENT_CATEGORY_SELECTION",
            FRAGMENT_RECIPE_SELECTION = "FRAGMENT_RECIPE_SELECTION";
    int categoryId, recipeId;
    String title;

    // tag for logging purposes
    private static final String TAG = CookbookActivity.class.getSimpleName();

    // tags for dialog purposes
    private final String DIALOG_NEW_RECIPE = "DIALOG_NEW_RECIPE",
            DIALOG_EDIT_RECIPE = "DIALOG_EDIT_RECIPE",
            DIALOG_DELETE_RECIPE = "DIALOG_DELETE_RECIPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook);

        // set to "null" -1
        this.categoryId = -1;
        this.recipeId = -1;

        // initializing layout vars
        toolbar = findViewById(R.id.toolbar);
        button_new_recipe = findViewById(R.id.button_new_recipe);

        // setting up toolbar
        setSupportActionBar(toolbar);

        // make FAB clickable
        button_new_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: prompt user -- new Recipe or new Category?

                // start new RecipeCreationDialogFragment
                recipeCreation = new RecipeCreationDialogFragment();
                recipeCreation.show(getFragmentManager(), DIALOG_NEW_RECIPE);
            }
        });

        // fragment logic handling for tablet
        if (isTablet()) {
            Log.d(TAG, "TABLET");
            openCategorySelection();
        } else {
            Log.d(TAG, "MOBILE PHONE");

            // set up fragment content -- default to category selection
            if (savedInstanceState == null) {
                openCategorySelection();
            } else {
                Log.d(TAG, "not resetting category selection");
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save toolbar title
        title = getSupportActionBar().getTitle().toString();
        savedInstanceState.putString(KEY_TOOLBAR_TITLE, title);

        // save tablet fragments
        if (isTablet()) {
            Log.d(TAG, "saving categoryId " + categoryId + " and recipeId " + recipeId + " in activity");
            savedInstanceState.putInt(FRAGMENT_CATEGORY_SELECTION, categoryId);
            savedInstanceState.putInt(FRAGMENT_RECIPE_SELECTION, recipeId);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // restore toolbar title
        title = savedInstanceState.getString(KEY_TOOLBAR_TITLE);
        if (title != null && !title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }

        // restore tablet fragments
        if (isTablet()) {
//            Log.d(TAG, "restoring categoryId " + categoryId + " and recipeId " + recipeId + " in activity");
            this.categoryId = savedInstanceState.getInt(FRAGMENT_CATEGORY_SELECTION);
            this.recipeId = savedInstanceState.getInt(FRAGMENT_RECIPE_SELECTION);

            // restore saved fragments
            if (this.categoryId >= 0) {
                Log.d(TAG, "reopening recipes in category " + categoryId);
                openRecipesInCategory(categoryId);
            }
            if (this.recipeId >= 0) {
                Log.d(TAG, "reopening recipe " + recipeId);
                openRecipeDetails(recipeId);
            }
        }
    }

    @Override
    public void updateToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void openCategorySelection() {
        Log.d(TAG, "switching to CategorySelectionFragment");

        // dynamically set up Category Selection fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // reset category and recipe IDs to null (-1)
        this.categoryId = -1;
        this.recipeId = -1;

        if (isTablet(this)) {
            ft.replace(R.id.fragment_cookbook_first, new CategorySelectionFragment());
            ft.replace(R.id.fragment_cookbook_second, new EmptyFragment());
            ft.replace(R.id.fragment_cookbook_main, new EmptyFragment());
        } else {
            // mobile phone layout
            ft.replace(R.id.fragment_cookbook, new CategorySelectionFragment());
            // if there was an up-navigation, uncomment following line
//                .addToBackStack(CategorySelectionFragment.class.getSimpleName());
        }
        ft.commit();
    }

    @Override
    public void categoryNameClicked(Category category) {
        Log.d(TAG, "opening " + category.getCategory() + " from RecipeDetails");
        this.categoryId = category.getId();
        openRecipesInCategory(this.categoryId);
    }

    @Override
    public void openRecipesInCategory(int categoryId) {
        Log.d(TAG, "opening recipes in category #" + categoryId);
        this.categoryId = categoryId;

        // pass recipe list to fragment
        RecipeSelectionFragment fragment = RecipeSelectionFragment.newInstance(categoryId);

        // dynamically set up Recipe Selection fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // change fragment view
        if (isTablet(this)) {
            ft.replace(R.id.fragment_cookbook_second, fragment)
//                    .addToBackStack(RecipeSelectionFragment.class.getSimpleName())
                    .commit();
        } else {
            // mobile phone layout
            ft.replace(R.id.fragment_cookbook, fragment)
                .addToBackStack(RecipeSelectionFragment.class.getSimpleName())
                .commit();
        }
    }

    @Override
    public void openRecipeDetails(int recipeId) {
        // open RecipeDetails for recipe with ID recipeId
        Log.d(TAG, "opening RecipeDetails for recipe #" + recipeId);

        this.recipeId = recipeId;

        // dynamically set up Recipe Selection fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(recipeId);

        // change fragment view
        if (isTablet(this)) {
            ft.replace(R.id.fragment_cookbook_main, fragment)
//                    .addToBackStack(RecipeDetailsFragment.class.getSimpleName())
                    .commit();
        } else {
            // mobile phone layout
            ft.replace(R.id.fragment_cookbook, fragment)
                    .addToBackStack(RecipeDetailsFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void editRecipe(Recipe recipe) {
        // start new RecipeCreationDialogFragment
        recipeModification = RecipeModificationDialogFragment.newInstance(recipe);
        recipeModification.show(getFragmentManager(), DIALOG_EDIT_RECIPE);
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        // delete recipe from cookbook DB
        recipeDeletion = RecipeDeletionDialogFragment.newInstance(recipe);
        recipeDeletion.setStyle(DialogFragment.STYLE_NORMAL, R.style.DeleteDialog);
        recipeDeletion.show(getFragmentManager(), DIALOG_DELETE_RECIPE);
    }

    @Override
    public boolean isTablet() {
        return isTablet(this);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void back() {
        this.onBackPressed();
    }

}
