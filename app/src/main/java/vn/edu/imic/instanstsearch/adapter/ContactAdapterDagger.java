package vn.edu.imic.instanstsearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.edu.imic.instanstsearch.R;
import vn.edu.imic.instanstsearch.interfaces.OnContactListener;
import vn.edu.imic.instanstsearch.network.model.Contact;

/**
 * Created by MyPC on 30/05/2018.
 */

public class ContactAdapterDagger extends RecyclerView.Adapter<ContactAdapterDagger.ContactHolder>{
    private List<Contact> listContact;
    private OnContactListener contactListener;
    public ContactAdapterDagger(OnContactListener contactListener) {
        this.contactListener = contactListener;
        listContact = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Contact contact = listContact.get(position);
        holder.bindContact(contact);
    }

    @Override
    public int getItemCount() {
        return listContact.size();
    }

    public void setData(List<Contact> list){
        listContact.clear();
        listContact.addAll(list);
        notifyDataSetChanged();
    }
    public class ContactHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_thumbnail)
        ImageView imgThumbnail;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        Context context;
        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(v ->
                contactListener.onContactClick(getAdapterPosition()));
        }

        public void bindContact(Contact contact){
            Glide.with(context)
                    .load(contact.getImageProfile())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgThumbnail);
            tvName.setText(contact.getName());
            tvPhone.setText(contact.getPhone());
        }
    }
}
