package com.dohman.myexercise;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dohman.myexercise.R;

import java.util.List;

public class UserBaseAdapter extends android.widget.BaseAdapter {

    private List<User> users;

    private int resource;

    private Context context;


    public UserBaseAdapter(Context context, int resource, List<User> users) {

        this.context = context;
        this.users = users;
        this.resource = resource;
    }

    @Override
    public int getCount() {

        int nMovies = 0;
        if (users != null)
            nMovies = users.size();

        return nMovies;
    }


    @Override
    public long getItemId(int position) {

        return 0;
    }

    class ViewHolder {

        TextView userName;
        ImageView image;
        TextView repo;
    }


    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {

            view = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.userName = (TextView) view.findViewById(R.id.title);
            holder.image = (ImageView) view.findViewById(R.id.image);
            holder.repo = (TextView) view.findViewById(R.id.repo);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }

        User user = users.get(position);

        Glide.with(context).load(user.getAvatar_url()).into(holder.image);
        holder.userName.setText(user.getLogin());
        holder.repo.setText(Integer.toString(user.getPublic_repos()));

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

}