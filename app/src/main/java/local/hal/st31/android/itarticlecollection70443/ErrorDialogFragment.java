package local.hal.st31.android.itarticlecollection70443;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class ErrorDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        Activity activity =getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Bundle extras = getArguments();
        String msg = "";
        if(extras != null){
            msg = extras.getString("msg");
        }
        builder.setMessage(msg);
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
