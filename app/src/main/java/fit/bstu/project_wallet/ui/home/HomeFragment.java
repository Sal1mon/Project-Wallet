package fit.bstu.project_wallet.ui.home;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import fit.bstu.project_wallet.R;
import fit.bstu.project_wallet.units.Category;

import static android.content.Context.CLIPBOARD_SERVICE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        if (!homeViewModel.keyCointais()) {
            showHiDialog();

        }
        final TextView textView = root.findViewById(R.id.balance_value);
        final TextView keyView = root.findViewById(R.id.key_value);


        homeViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s + " BYN"));
        homeViewModel.getKey().observe(getViewLifecycleOwner(), keyView::setText);
        root.findViewById(R.id.refresh).setOnClickListener(v -> {
            homeViewModel.removeSharedPrefs();
            showHiDialog();
        });
        root.findViewById(R.id.exit).setOnClickListener(v -> {
            System.exit(0);
        });
        root.findViewById(R.id.addCash).setOnClickListener(v -> {
            showAlertDialogButtonClicked(homeViewModel.returnCategoryList(1), 1);
        });
        root.findViewById(R.id.removeCash).setOnClickListener(v -> {
            showAlertDialogButtonClicked(homeViewModel.returnCategoryList(2), 2);
        });
        root.findViewById(R.id.key_value).setOnClickListener(v -> {
            ClipboardManager myClipboard;
            myClipboard = (ClipboardManager) requireContext().getSystemService(CLIPBOARD_SERVICE);
            ClipData myClip;
            String text = homeViewModel.getSharedPreferenseKey();
            myClip = ClipData.newPlainText("text", text);
            myClipboard.setPrimaryClip(myClip);
            Toast toast = Toast.makeText(requireContext(), "Key Copied!", Toast.LENGTH_LONG);
            toast.show();
        });
        return root;
    }

    public void showAlertDialogButtonClicked(List<Category> categories, int type) {
        StringBuffer value = new StringBuffer("-");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Enter you cartegory, and cash value");
        View customLayout = getLayoutInflater().inflate(R.layout.alert_layout, null);
        Spinner spinner = customLayout.findViewById(R.id.spinnerID);
        EditText a = customLayout.findViewById(R.id.editTextValue);
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(adapter);

        builder.setView(customLayout);

        builder.setPositiveButton("OK", (dialog, which) -> {

            if (a.getText().toString().isEmpty() || (a.getText().toString().length() > 8)) {
                Toast toast = Toast.makeText(requireContext(),
                        "Input Correct Value", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Category f = (Category) spinner.getSelectedItem();
                switch (type) {
                    case 1:
                        homeViewModel.trasactionCreate(f.getId(), Integer.parseInt(a.getText().toString()));
                        break;
                    case 2:
                        value.append(a.getText().toString());
                        homeViewModel.trasactionCreate(f.getId(), Integer.parseInt(value.toString()));
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showHiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View customLayout = getLayoutInflater().inflate(R.layout.customdialog_hi, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        EditText wallet = customLayout.findViewById(R.id.editKeyWalltSync);
        customLayout.findViewById(R.id.btn_yes).setOnClickListener(v -> {
            homeViewModel.syncronizeWallet(wallet.getText().toString());
            if (homeViewModel.keyCointais()) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Sync Sucses", Toast.LENGTH_SHORT).show();
            }
        });
        customLayout.findViewById(R.id.btn_no).setOnClickListener(v -> {
            homeViewModel.createNewKey();
            dialog.dismiss();
        });

        dialog.show();
    }
}