package br.com.firstsoft.historicodenotificacoes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.firstsoft.historicodenotificacoes.R;
import br.com.firstsoft.historicodenotificacoes.model.CPackage;
import br.com.firstsoft.historicodenotificacoes.viewholder.PackageViewHolder;

/**
 * Created by root on 01/06/17.
 */

public class PackageRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<CPackage> packageList;
    private Context context;
    public PackageRecyclerViewAdapter(List<CPackage> packageList, Context context) {
        this.packageList = packageList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pacote, parent, false);
        PackageViewHolder viewHolder = new PackageViewHolder(view, this.context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PackageViewHolder viewHolder = (PackageViewHolder) holder;
        try {
            CPackage cPackage = packageList.get(position);

            viewHolder.appIcon.setImageDrawable(cPackage.getPackageIcon());
            viewHolder.appName.setText(cPackage.getAppName());
            viewHolder.notificationNumber.setText("Quantidade: " + String.valueOf(cPackage.getChildCount()));
        } catch (Exception ex) {

        }
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public void clear() {
        this.packageList.clear();
    }

    public void add(CPackage cPackage) {
        this.packageList.add(cPackage);
        notifyDataSetChanged();
    }
}
