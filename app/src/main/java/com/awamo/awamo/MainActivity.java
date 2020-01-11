package com.awamo.awamo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.awamo.awamo.adapters.ResultsAdapter;
import com.awamo.awamo.models.Results;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ResultsAdapter adapter;
    private List<Results> resultList;

    //Views variable
    EditText number_one,number_two;
    private Spinner spinner1;
    Button calculate;
    ProgressDialog progressDialog;

    //Operations Variables
    String Operations = "";
    String Add = "Add";
    String Subtract = "Subtract";
    String Multply ="Multply";
    String expr;

    //Api url
    String url = "http://api.mathjs.org/v4/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Binding views
        recyclerView = findViewById(R.id.recycleview);
        number_one = findViewById(R.id.number_one);
        number_two = findViewById(R.id.number_two);
        calculate = findViewById(R.id.cal);
        spinner1 = findViewById(R.id.spinner1);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        //Set listeners to button and spinner
        calculate.setOnClickListener(this);
        spinner1.setOnItemSelectedListener(this);

        resultList = new ArrayList<>();
        adapter = new ResultsAdapter(this, resultList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        //Geeting values from the edittext into varibles
        String num_one = number_one.getText().toString();
        String num_two = number_two.getText().toString();

        //Operational signs variable
        String operation_add = "+";
        String operation_sub = "-";
        String operation_div = "/";
        String operation_mul = "*";


        //Generate expression from here based on which operation
        if(Operations.equals(Add)){
            expr = num_one+ " " + operation_add + " "+ num_two;
        }else if(Operations.equals(Subtract)){
            expr = num_one+ " " + operation_sub + " "+ num_two;
        }else if(Operations.equals(Multply)){
            expr = num_one+ " " + operation_mul + " "+ num_two;
        }else {
            expr = num_one+ " " + operation_div + " "+ num_two;
        }

        //Hashmap to store params that will be passed as json object to the url
        HashMap data = new HashMap();
        data.put("expr",expr);

        if(num_one.isEmpty() && num_two.isEmpty()){
            Toast.makeText(getApplicationContext(), " Please select numbers.", Toast.LENGTH_SHORT).show();
        }else{
            //Pass data to the API via the passed url and data
          postData(url,data,num_one,num_two);
        }


    }


    public void postData(String url, HashMap data, final String numberone, final String numbertwo){
        progressDialog.setMessage("Please wait..");
        showDialog();
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            int result  = response.getInt("result");
                            int operation_response = 0;

                            Results a;
                            Random rand = new Random();
                            float rand_int1 = Math.round(rand.nextFloat());
                            if(rand_int1 == 1){
                                float rand_int2 = rand.nextFloat();
                                operation_response = (int) Math.ceil(rand_int2 * 4000);
                                a = new Results(numberone,numbertwo,operation_response,result,"No");

                            }else{
                                operation_response = result;
                                a = new Results(numberone,numbertwo,operation_response,result,"Yes");

                            }

                            resultList.add(a);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        hideDialog();
                    }
                }
        ){
            //here I want to post data to sever
        };
        requstQueue.add(jsonobj);

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Operations = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
