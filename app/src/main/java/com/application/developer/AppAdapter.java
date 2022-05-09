package com.application.developer;

import android.widget.BaseAdapter;
import java.util.LinkedList;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class AppAdapter extends BaseAdapter
{

    private LinkedList<AppList> mData;
    private Context mContext;

    public AppAdapter(LinkedList<AppList> mData,Context mContext)
    {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.empty_view,parent,false);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.appicon);
        TextView txt_aName = (TextView) convertView.findViewById(R.id.appname);
        TextView txt_aSpeak = (TextView) convertView.findViewById(R.id.apppackage);
        TextView txt_aPath = (TextView) convertView.findViewById(R.id.apppath);
        //img_icon.setBackgroundResource(mData.get(position).getappIcon());
        Bitmap bitmap = BitmapFactory.decodeFile(mData.get(position).getappIcon());
        img_icon.setImageBitmap(bitmap);
        txt_aName.setText(mData.get(position).getappName());
        txt_aSpeak.setText(mData.get(position).getappPackage());
        txt_aPath.setText(mData.get(position).getappPath());
        return convertView;
    }
}
