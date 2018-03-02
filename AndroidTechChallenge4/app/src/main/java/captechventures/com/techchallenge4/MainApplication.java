package captechventures.com.techchallenge4;

import android.app.Application;
import android.util.Log;

/**
 * Created by mluansing on 10/19/17.
 */

public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "TechChallenge4 Recipe Cookbook started!");

    }

    /**
     * extend Fragment (appcompat equivalent)
     *
     * CategorySelectionFragment
     * display list of recipe categories
     * click on category --> list of recipes within category
     *
     * RecipeSelectionFragment
     * display list of recipes within category
     * show 1) recipe name and 2) cook time
     * click on recipe --> display recipe details
     *
     * RecipeDetailsFragment
     * display recipe details
     * edit recipe details (not in spec)
     * click delete recipe button --> confirm delete
     * BACK button should go to previous screen (whether just created new / chosen from list)
     *
     * RecipeCreation (or Dialog Frament)?
     * accessible at all times -- "New Recipe" button on all activities?
     * after creation --> RecipeDetailsActivity
     * spinner dropdown of categories
     *
     * RecipeModification
     * opens details of newly updated Recipe
     *
     * RecipeDeletion
     * DialogFragment and must confirm deletion
     *
     * TODO: add unit of 'cooktime' in RecipeCreation / RecipeModification
     *
     */
}
