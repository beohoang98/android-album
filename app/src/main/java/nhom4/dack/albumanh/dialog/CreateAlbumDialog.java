package nhom4.dack.albumanh.dialog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import nhom4.dack.albumanh.R;

public class CreateAlbumDialog extends AppCompatDialog {
    private Button cancel, add;
    private TextInputEditText inputText;
    private OnSubmitAlbumName onSubmitAlbumName;

    public CreateAlbumDialog(Context ctx) {
        super(ctx);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_create_album_dialog);

        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);
        inputText = findViewById(R.id.input_album_name);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSubmitAlbumName != null) {
                    String input = inputText.getText() != null ? inputText.getText().toString() : "";

                    if (input.replaceAll("\\s+", "").length() == 0) {
                        Toast.makeText(getContext(), R.string.invalid_album_name, Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    String trimmed = input.trim();
                    onSubmitAlbumName.onSubmit(trimmed);
                    cancel();
                }
            }
        });
    }

    public void setOnSubmitAlbumName(OnSubmitAlbumName onSubmitAlbumName) {
        this.onSubmitAlbumName = onSubmitAlbumName;
    }

    public interface OnSubmitAlbumName {
        void onSubmit(@NonNull String albumName);
    }
}
