package com.tesis.alejofila.centrocomercial.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tesis.alejofila.centrocomercial.R;
import com.tesis.alejofila.centrocomercial.model.Oferta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejofila on 5/09/15.
 */
public class OfertaAdapter extends BaseAdapter {
    ArrayList<Oferta> ofertas = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public OfertaAdapter(Context context, ArrayList<Oferta> ofertas) {
        super();
        this.context = context;
        this.ofertas = ofertas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
       return  ofertas.size();
    }

    @Override
    public Object getItem(int position) {
        return ofertas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.oferta_item, null);
            holder = new ViewHolder();
            holder.nombreProducto = (TextView) convertView.findViewById(R.id.oferta_nombre);
            holder.precioProducto = (TextView) convertView.findViewById(R.id.oferta_precio);
            holder.imagenProducto = (ImageView) convertView.findViewById(R.id.oferta_imagen);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Oferta oferta = ofertas.get(position);
        holder.precioProducto.setText(oferta.getPrecio());
        Picasso.with(context).load("URL")
                .fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imagenProducto);

        return convertView;
    }

    static class ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto;
        TextView precioProducto;

    }

}