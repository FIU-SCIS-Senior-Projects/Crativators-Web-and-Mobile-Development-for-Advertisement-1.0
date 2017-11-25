package com.example.positivepathways;

        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.Button;

public class CheckDelete extends DialogFragment {
    Button button_Yes,button_No;
    static String DialogBoxTitle;

    public interface CheckDeleteListener {
        void onSelection(boolean choice);
    }

    //must have this...
    public CheckDelete(){

    }

    public void setDialogTitle(String title) {
        DialogBoxTitle = title;
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState ) {

        View view= inflater.inflate(R.layout.certain_delete, container);
        //sets the buttons
        button_Yes = (Button) view.findViewById(R.id.delete_button_Yes);
        button_No = (Button) view.findViewById(R.id.delete_button_No);

        button_Yes.setOnClickListener(btnListener);
        button_No.setOnClickListener(btnListener);

        getDialog().setTitle(DialogBoxTitle);

        return view;
    }

    /**
     * assigns the functions of the clicked button
     */
    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            boolean state;
            CheckDeleteListener activity = (CheckDeleteListener) getActivity();
            if(((Button) v).getText().toString().equals("Yes"))
                state = true;
            else
                state = false;
            activity.onSelection(state);
            //remove the dialog
            dismiss();
        }
    };
}