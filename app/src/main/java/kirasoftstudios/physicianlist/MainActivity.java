package kirasoftstudios.physicianlist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    //list of physicians
   List<Physician> physicians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create new instance of physicians list
        physicians = new ArrayList<>();

        //set up rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CONSTANTS.URL)
                .build();

        //create instance of rest handler
        RestHandler restHandler = restAdapter.create(RestHandler.class);



        restHandler.getPhysicians(new Callback<List<Physician>>() {


            @Override
            public void success(List<Physician> physician, Response response) {
                physicians = physician;
                ShowText();
            }

            @Override
            public void failure(RetrofitError error) {
                ShowBadText(error.getMessage());
            }


        });





    }

    private void ShowBadText(String e)
    {
        Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();

    }

    private void ShowText()
    {
        String p = physicians.get(0).getName();
        Toast.makeText(this, p, Toast.LENGTH_LONG).show();

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
}
