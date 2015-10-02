package kirasoftstudios.physicianlist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    ListView listView;
    Button sortByName, sortByPractice, reverseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create new instance of physicians list
        physicians = new ArrayList<>();

        //create new instance of listview to display in xml
        listView = (ListView)findViewById(R.id.doctorsList);

        //create new isntance of buttons
        sortByName = (Button)findViewById(R.id.sortByName);
        sortByPractice = (Button)findViewById(R.id.sortByPractice);
        reverseList = (Button)findViewById(R.id.reverseList);

        //sort by name clicked
        sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortByName();
            }
        });

        //sort by practice clicked
        sortByPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortByPractice();
            }
        });

        //reverse list clicked
        reverseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReverseOrder();
            }
        });

        //set up rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CONSTANTS.URL)
                .build();

        //create instance of rest handler
        RestHandler restHandler = restAdapter.create(RestHandler.class);


        //retrive all physicians from url
        restHandler.getPhysicians(new Callback<List<Physician>>() {


            @Override
            public void success(List<Physician> physician, Response response) {
                physicians = physician;
                SortByName();
            }

            @Override
            public void failure(RetrofitError error) {
                ShowBadText(error.getMessage());
            }


        });





    }


    //there was an error nad the information could not be received
    private void ShowBadText(String e)
    {
        Toast.makeText(this, "Sorry. There Was An Error. Please Try Again", Toast.LENGTH_LONG).show();

    }


    //<summary>
    //Put all physicians into a listview and display
    //</summary>
    private void ShowText()
    {
        //create array form physicians list
        List<String> physList = new ArrayList<String>();
        for(Physician p : physicians)
        {
            physList.add(p.getName() + ", " + p.getSpecialty());
        }

        String[] fullPhys = physList.toArray(new String[physList.size()]);

        //doctor array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, fullPhys);


        //hook adapter up to list view
        listView.setAdapter(adapter);

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

        ShowText();

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

        ShowText();
    }

    //<summary>
    //Reverse Doctors List
    //</summary>
    private void ReverseOrder()
    {
        Collections.reverse(physicians);
        ShowText();
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
