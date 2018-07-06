package vn.edu.imic.instanstsearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class ContactAdapterFilterDagger extends RecyclerView.Adapter<ContactAdapterFilterDagger.ContactHolder>
        implements Filterable {
    private Context mContext;
    //Danh sách ban đầu
    private List<Contact> mListContact;
    //Danh sách sau khi lọc
    private List<Contact> mListContactFiltered;
    private OnContactListener contactListener;

    public ContactAdapterFilterDagger(Context mContext, List<Contact> listContact, OnContactListener contactListener) {
        this.mContext = mContext;
        this.mListContact = listContact;
        this.mListContactFiltered = listContact;
        this.contactListener = contactListener;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact contact = mListContactFiltered.get(position);
        holder.bindContact(contact);
    }

    @Override
    public int getItemCount() {
        return mListContactFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //Chuỗi nhập vào
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    //Nếu chưa nhập ký tự thì list được lọc là list ban đầu
                    mListContactFiltered = mListContact;
                }else {
                    List<Contact> filteredList = new ArrayList<>();
                    for (Contact row: mListContact){
                        /*Tìm kiếm theo tên hoặc số điện thoại phù hợp*/
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getPhone().contains(constraint)){
                            filteredList.add(row);
                        }
                    }
                    mListContactFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListContactFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListContactFiltered = (List<Contact>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_thumbnail)
        ImageView imgThumbnail;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(v -> contactListener.onContactClick(getAdapterPosition()));
        }

        public void bindContact(Contact contact){
            Glide.with(mContext).load(contact.getImageProfile())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgThumbnail);
            tvName.setText(contact.getName());
            tvPhone.setText(contact.getPhone());
        }
    }
}
