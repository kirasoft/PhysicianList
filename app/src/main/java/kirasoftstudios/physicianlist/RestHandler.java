package kirasoftstudios.physicianlist;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Kota on 10/2/2015.
 */
public interface RestHandler {

    //<summary>
    //Handle all url requests here
    //</summary>

    //Get All Physicians
    @GET ("/android_challenge/physicians")
    void getPhysicians(Callback<Physician> physicianCallback);

}
