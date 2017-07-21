package com.pagatodo.apolo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.adapters.CustomAdapter;
import com.pagatodo.apolo.data.pojo.Cards;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.apolo.utils.RecyclerItemClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.ui.UI.showToast;

public class MainActivity extends AppCompatActivity  {
    private final String TAG = "MainActivity";
    private CustomAdapter adapter;
    private List<Cards> cardsList;
    @BindView(R.id.recycler_view_card) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cardsList = new ArrayList<>();
        adapter = new CustomAdapter(getApplicationContext(), cardsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new  RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Intent i = new Intent(MainActivity.this, CaptureActivity.class);
                        i.putExtra(Constants.TYPE_CAPTURE,position);
                        startActivity(i);
                    }
                })
        );

        dummyData();
    }

    @OnClick(R.id.action_next)
    public void next() {
       //actividad
    }

    /*** Checking device has camera hardware or not * */
    public boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true; // this device has a camera
        } else {
            return false; // no camera on this device
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isDeviceSupportCamera()) {
            showToast(getString((R.string.not_compatible_camera)), getApplicationContext());
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void update(){
        adapter.notifyDataSetChanged();
    }
    public void dummyData() {
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, Constants.URL_DEBUG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray objects = jsonObj.getJSONArray("cards");
                            for(int i=0; i < objects.length(); i++){
                                    JSONObject item = objects.getJSONObject(i);
                                    Cards items = new Cards();
                                    items.setTypeCard(item.getString("card"));
                                    items.setThumbCard(item.getString("thumb"));
                                    cardsList.add(items);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("printStackTrace"," »»»»»» ");
                        }
                        adapter.notifyDataSetChanged();
                        update();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Home ", "Error: " + error.getMessage());
            }
        });
        App.getInstance().addToRequestQueue(jsonObjReq);
    }

}
