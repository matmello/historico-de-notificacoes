package br.com.firstsoft.historicodenotificacoes.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.firstsoft.historicodenotificacoes.R;

/**
 * Created by root on 01/06/17.
 */

public class PackageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final public TextView appName;
    final public ImageView appIcon;
    final public TextView notificationNumber;
    final private Context context;


    public PackageViewHolder(View itemView, Context context) {
        super(itemView);
        this.appName = (TextView) itemView.findViewById(R.id.package_app_name);
        this.appIcon = (ImageView) itemView.findViewById(R.id.package_app_icon);
        this.notificationNumber = (TextView) itemView.findViewById(R.id.package_children_count);
        this.context = context;
        appName.setOnClickListener(this);
        appIcon.setOnClickListener(this);
        notificationNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Clique", Toast.LENGTH_SHORT).show();
    }
}
