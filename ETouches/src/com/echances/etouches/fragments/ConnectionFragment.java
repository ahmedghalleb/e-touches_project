package com.echances.etouches.fragments;

import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 
 * @file ConnectionFragment.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from Fragment.
 * @details *
 * 
 */
public class ConnectionFragment extends BaseFragment
{

    Button mConnectionButton;
    Button mInscriptionButton;
    Button mLaterButton;

    public ConnectionFragment() {
        super();
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //		setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_connection, null);
        mConnectionButton = (Button)view.findViewById(R.id.connection_button);
        mInscriptionButton = (Button)view.findViewById(R.id.inscription_button);
        mLaterButton = (Button)view.findViewById(R.id.later_button);
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


        mConnectionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                LoginFragment fragment = new LoginFragment();
                ((ConnectionActivity)getActivity()).addContent(fragment, "0");

            }
        });

        mInscriptionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                InscriptionFragment fragment = new InscriptionFragment();
                ((ConnectionActivity)getActivity()).addContent(fragment, "0");

            }
        });

        mLaterButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

            }
        });

    }

    @Override
    public void onResume ()
    {
        super.onResume();

    }



}
