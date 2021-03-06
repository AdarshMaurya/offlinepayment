package com.softhinkers_wallet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.softhinkers_wallet.interfaces.ContactsDelegate;
import com.softhinkers_wallet.smartcoinswallet.AddEditContacts;
import com.softhinkers_wallet.smartcoinswallet.ContactListAdapter;
import com.softhinkers.offlinepayment.R;
import com.softhinkers_wallet.smartcoinswallet.ShareContact;

/**
 * Created by adarsh on 05/14/17.
 */

public class ContactsFragment extends Fragment implements ContactsDelegate {
    public static ContactsDelegate contactsDelegate;

   @BindView(R.id.contactslist)
    ListView contactslist;
    ContactListAdapter adapter;

   @BindView(R.id.sharecontact)
    ImageView sharecontact;

   @BindView(R.id.addcontact)
    ImageView addcontact;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        contactsDelegate = this;
        adapter = new ContactListAdapter(getActivity());
        contactslist.setAdapter(adapter);
        return view;

    }

    @OnClick(R.id.addcontact)
    public void AddContact() {
        Intent intent = new Intent(getActivity(), AddEditContacts.class);
        intent.putExtra("activity", 99999);
        startActivity(intent);
    }

    @Override
    public void OnUpdate(String s, int id) {
        adapter.loadmore();
        adapter = new ContactListAdapter(getActivity());
        contactslist.setAdapter(adapter);
    }

    @OnClick(R.id.sharecontact)
    public void ShareContact() {
        Intent intent = new Intent(getActivity(), ShareContact.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible) {
            addcontact.setVisibility(View.INVISIBLE);
            final Animation animationRigthtoLeft = AnimationUtils.loadAnimation(getContext(), R.anim.animation);
            animationRigthtoLeft.setInterpolator(new AccelerateDecelerateInterpolator());
            animationRigthtoLeft.setDuration(333);
            addcontact.postDelayed(new Runnable() {
                public void run() {
                    addcontact.startAnimation(animationRigthtoLeft);
                    addcontact.setVisibility(View.VISIBLE);
                }
            }, 333);
        }
    }
}
