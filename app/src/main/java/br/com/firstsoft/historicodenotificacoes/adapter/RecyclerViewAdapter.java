package br.com.firstsoft.historicodenotificacoes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import br.com.firstsoft.historicodenotificacoes.R;
import br.com.firstsoft.historicodenotificacoes.model.CNotification;
import br.com.firstsoft.historicodenotificacoes.viewholder.NotificationViewHolder;

/**
 * Created by Danilo on 27/05/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<CNotification> notificationList;
    private Context context;

    public RecyclerViewAdapter(List<CNotification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_notificacao, parent, false);
        NotificationViewHolder viewHolder = new NotificationViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NotificationViewHolder viewHolder = (NotificationViewHolder) holder;
        try {
            CNotification notification = notificationList.get(position);

            viewHolder.titulo.setText(notification.getTitle());
            viewHolder.appName.setText(notification.getAppName());
            viewHolder.icone.setImageDrawable(notification.getAppIcon());
            viewHolder.horario.setText(notification.getData());
        } catch (Exception ex) {

        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void add(CNotification notification) {
        this.notificationList.add(notification);
        notifyDataSetChanged();
    }

    public void clear() {
        this.notificationList.clear();
    }
}
