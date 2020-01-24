package com.example.pratice;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import static android.content.ContentValues.TAG;

public class HttpUtils extends AsyncTask<Void,Void,String> {

    //High priority UI variables goes below.....
    private ProgressDialog progressDialog;

    //Medium priority NON-UI variables goes below...
    private Context context;

    private NetworkResponseListener listener;
    private Exception exception;
    private ArrayList<employeePOJO> empDataList;



    public HttpUtils(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.pleaseWaitSpelling));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        empDataList = new ArrayList<>();
    }


    public void setlistner(NetworkResponseListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub

        progressDialog.show();

        super.onPreExecute();
    }//onPreExecute closes here....

    @Override
    protected String doInBackground(Void... voids) {

        // Do some validation here

        try {
            URL url = new URL("https://dummy.restapiexample.com/api/v1/employees");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line).append("\n");


                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        //Stop the progressDialog here...
        if (progressDialog != null && progressDialog.isShowing())

            try {
                progressDialog.dismiss();//Closing the Dialog here....
            } catch (Exception e) {
                //Log.d(TAG, "Cant not dismiss" +e.getMessage());
            }
        Log.d(TAG, "Response = " + response);

        try {
            JSONObject reader = new JSONObject(response);

            JSONArray data = reader.getJSONArray("data");


            for (int i = 0; i < data.length(); i++) {
                JSONObject employeeObj = data.getJSONObject(i);

                String id = employeeObj.getString("id");
                String employee_name = employeeObj.getString("employee_name");
                String employee_salary = employeeObj.getString("employee_salary");
                String employee_age = employeeObj.getString("employee_age");
                String profile_image = employeeObj.getString("profile_image");

                employeePOJO temp = new employeePOJO();
                temp.setId(id);
                temp.setEmployee_name(employee_name);
                temp.setEmployee_salary(employee_salary);
                temp.setEmployee_age(employee_age);
                temp.setProfile_image(profile_image);


                empDataList.add(temp);

            }

            if (reader.getString("status").equalsIgnoreCase("success")) {

                listener.notifyNetworkResponseSuccess(empDataList);
            } else {
                listener.notifyNetworkResponseFailure();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    }


