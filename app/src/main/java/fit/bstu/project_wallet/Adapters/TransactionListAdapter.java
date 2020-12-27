package fit.bstu.project_wallet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fit.bstu.project_wallet.R;
import fit.bstu.project_wallet.units.Transaction;
import fit.bstu.project_wallet.databinding.RecyclerviewItemBinding;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {
    private final LayoutInflater mInflater;
    private List<Transaction> mWords; // Cached copy of words

    public TransactionListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }
    @NotNull
    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerviewItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recyclerview_item, parent, false);
        return new TransactionViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.bind(mWords.get(position));
    }

    public Transaction getWordAtPosition (int position) {
        return mWords.get(position);
    }

    public void setContacts(List<Transaction> words){
        mWords = words;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
    class TransactionViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewItemBinding binding;

        private TransactionViewHolder(RecyclerviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Transaction transaction) {
            binding.setTransaction(transaction);
            binding.executePendingBindings();
        }
    }
}
