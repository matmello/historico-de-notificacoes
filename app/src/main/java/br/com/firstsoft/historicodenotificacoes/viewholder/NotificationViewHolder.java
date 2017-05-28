package br.com.firstsoft.historicodenotificacoes.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.firstsoft.historicodenotificacoes.R;

/**
 * Created by Danilo on 27/05/2017.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder{

    final public TextView titulo;
    final public TextView horario;
    final public ImageView icone;
    final public TextView appName;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        this.titulo = (TextView) itemView.findViewById(R.id.titulo_notificacao);
        this.horario = (TextView) itemView.findViewById(R.id.horario_notificacao);
        this.icone = (ImageView) itemView.findViewById(R.id.icone_notificacao);
        this.appName = (TextView) itemView.findViewById(R.id.nome_app_notificacao);
    }
}