package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.R;
import com.example.mywallet.WalletFragment;

import java.util.List;

import dao.AppDatabase;
import entity.Transaction;
import entity.Wallet;

public class WalletViewAdapter extends BaseAdapter{

private WalletFragment walletFragment;
    private Context context;
    private List<Wallet> listWallet;

    public WalletViewAdapter(Context context, List<Wallet> listWallet) {
        this.context = context;
        this.listWallet = listWallet;
    }

    public WalletViewAdapter(WalletFragment walletFragment, List<Wallet> wallets) {
        this.walletFragment = walletFragment;
        this.listWallet = wallets;
    }

    @Override
    public int getCount() {
        return listWallet.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < 0){
            return null;
        }
        return listWallet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wallet, null);
        }

        ImageView imageViewWallet = convertView.findViewById(R.id.imageViewWallet);
        TextView nameWallet = convertView.findViewById(R.id.nameWallet);
        TextView balanceWallet = convertView.findViewById(R.id.balanceWallet);

        Wallet wallet = listWallet.get(position);
//        imageViewWallet.setImageResource(wallet.get);
        nameWallet.setText(wallet.getName());
        balanceWallet.setText(Integer.toString(wallet.getBalance()));

        Button editWallet = convertView.findViewById(R.id.editWallet);
        Button editWalletMenu = convertView.findViewById(R.id.editWalletMenu);
        Button deleteWalletMenu = convertView.findViewById(R.id.deleteWalletMenu);

        editWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(parent.getContext(), editWallet);
                popupMenu.inflate(R.menu.layout_popup_menu);
                Menu menu = popupMenu.getMenu();
                menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(v.getContext(),"TEST",10);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        return convertView;
    }



}
