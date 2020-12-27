package fit.bstu.project_wallet.ui.history;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fit.bstu.project_wallet.Adapters.TransactionListAdapter;
import fit.bstu.project_wallet.R;
import fit.bstu.project_wallet.units.Transaction;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final TransactionListAdapter adapter = new TransactionListAdapter(requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyViewModel.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            adapter.setContacts(transactions);
        });
        ItemTouchHelper(adapter, recyclerView);
        return root;
    }

    public void ItemTouchHelper(TransactionListAdapter a, RecyclerView recyclerView) {
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Transaction transaction = a.getWordAtPosition(position);
                        Toast.makeText(requireContext(), "Transaction Deleted", Toast.LENGTH_LONG).show();

                        // Delete the word
                        historyViewModel.deleteTransac(transaction);
                        historyViewModel.getAllTransactions().observe(getViewLifecycleOwner(), a::setContacts);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }
}
