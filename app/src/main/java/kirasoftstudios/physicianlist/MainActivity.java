package kirasoftstudios.physicianlist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        Toast.makeText(this, "Sorry. There Was An Error. Please Try Again", Toast.LENGTH_LONG).show();

    }

    private void ShowText()
    {


    }

    //<summary>
    //Sort by Doctors Name Alphabetically
    //</summary>
    private void SortByName()
    {

        Collections.sort(physicians, new Comparator<Physician>() {
            @Override
            public int compare(final Physician object1, final Physician object2) {
                return object1.getName().compareTo(object2.getName());
            }
        } );
    }

    //<summary>
    //Sort by Doctors Practice Alphabetically
    //</summary>
    private void SortByPractice()
    {
        Collections.sort(physicians, new Comparator<Physician>() {
            @Override
            public int compare(final Physician object1, final Physician object2) {
                return object1.getSpecialty().compareTo(object2.getSpecialty());
            }
        } );
    }

    //<summary>
    //Reverse Doctors List
    //</summary>
    private void ReverseOrder()
    {
        Collections.reverse(physicians);

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
