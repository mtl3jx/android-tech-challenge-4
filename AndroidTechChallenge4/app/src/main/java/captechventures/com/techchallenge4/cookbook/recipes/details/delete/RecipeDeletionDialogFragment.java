package captechventures.com.techchallenge4.cookbook.recipes.details.delete;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.database.CookbookDatabase;
import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 11/8/17.
 */

public class RecipeDeletionDialogFragment extends DialogFragment {

    Recipe recipe;

    // layout vars
    TextView name;
    Button button_delete_yes, button_delete_no;

    // listener
    RecipeDeletionListener listener;

    private static final String KEY_ARG_RECIPE_DELETE = "KEY_ARG_RECIPE_DELETE",
            DELETE_CONFIRMATION = "Confirm Deletion";

    // tag for logging purposes
    private static final String TAG = RecipeDeletionDialogFragment.class.getSimpleName();

    public RecipeDeletionDialogFragment() {
        // required empty public constructor
    }

    public static RecipeDeletionDialogFragment newInstance(Recipe recipe) {
        RecipeDeletionDialogFragment fragment = new RecipeDeletionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG_RECIPE_DELETE, Parcels.wrap(recipe));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up listener
        Context context = getActivity();
        if (context instanceof RecipeDeletionListener) {
            listener = (RecipeDeletionListener) context;
        }

        // grab Recipe to delete from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = Parcels.unwrap(bundle.getParcelable(KEY_ARG_RECIPE_DELETE));
        } else {
            recipe = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_deletion, container,
                false);

        getDialog().setTitle(DELETE_CONFIRMATION);

        // init layout vars
        name = rootView.findViewById(R.id.delete_recipe_name);
        button_delete_yes = rootView.findViewById(R.id.button_delete_yes);
        button_delete_no = rootView.findViewById(R.id.button_delete_no);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (recipe == null) {
            Log.e(TAG, "No recipe found");
            dismiss();

        } else {
            // populate name
            StringBuilder confirmationMsg = new StringBuilder("Are you sure you want to delete the recipe for '");
            confirmationMsg.append(recipe.getName());
            confirmationMsg.append("'? It cannot be recovered once deleted.");
            name.setText(confirmationMsg.toString());

            button_delete_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Deletion confirmation button clicked");

                    // delete from DB
                    int deletedRows = CookbookDatabase.getInstance(getActivity()).recipeDao().delete(recipe);
                    if (deletedRows < 0) Log.e(TAG, "Error deleting Recipe from DB");

                    // open category selection
                    listener.openCategorySelection();

                    // show message confirming it was deleted
                    Toast.makeText(getActivity(), "Deleted " + recipe.getName(), Toast.LENGTH_SHORT).show();

                    // dismiss deletion dialog
                    dismiss();

                }
            });

            button_delete_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Deletion cancellation button clicked");
                    dismiss();
                }
            });

        }

    }

    public interface RecipeDeletionListener {
        // methods that this fragment may want to call from CookbookActivity
        void openCategorySelection();
    }

}
