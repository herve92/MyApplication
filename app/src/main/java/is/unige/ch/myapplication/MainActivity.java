package is.unige.ch.myapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.opencsv.CSVReader;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnKeyListener {

    private ArrayList<GarbagePlace> list = new ArrayList<GarbagePlace>();
    private ListAdapter adapter;
    private String FILE_DATA_CSV = "bennes.csv";
    private ListView listView;
    private EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.list);

        // Lecture + setter les données
        this.readCSV();

        this.adapter = new ListAdapter(this, R.layout.list_item, list);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(this);

        this.inputSearch = (EditText) findViewById(R.id.input_search);
        this.inputSearch.setOnKeyListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    //lire le fichier de données
    private void readCSV() {

        AssetManager assetManager = this.getAssets();

        try {

            InputStream csvStream = assetManager.open(FILE_DATA_CSV);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            // throw away the header
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {

                String numero = line[0];
                String address = line[8];
                double latitude = Double.parseDouble(line[2]);
                double longitude = Double.parseDouble(line[3]);

                String paper = line[13];
                String glass = line[14];
                String pet = line[15];

                System.out.println("numero: " + numero);
                System.out.println("address: " + address);
                System.out.println("latitude: " + latitude);
                System.out.println("longitude: " + longitude);
                System.out.println("paper: " + paper);
                System.out.println("glass: " + glass);
                System.out.println("pet: " + pet);

                // conditions pour laliste des déchets autoriser pour chaque adresse

                ArrayList<GarbagePlace.GARBAGE_TYPE> garbageSupported = new ArrayList<GarbagePlace.GARBAGE_TYPE>();
                if(this.isSupported(paper)) {
                    garbageSupported.add(GarbagePlace.GARBAGE_TYPE.PAPER);
                }
                if(this.isSupported(glass)) {
                    garbageSupported.add(GarbagePlace.GARBAGE_TYPE.GLASS);
                }
                if(this.isSupported(pet)) {
                    garbageSupported.add(GarbagePlace.GARBAGE_TYPE.PET);
                }

                GarbagePlace gp = new GarbagePlace(numero, address, latitude, longitude, garbageSupported);
                this.list.add(gp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isSupported(String type) {
        if(type != null) {
            if (type.toLowerCase().equals("oui")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        GarbagePlace gp = this.adapter.getItem(position);

        Toast.makeText(this, "Element: " + position + " = " + gp.getAddress(), Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("address", gp.getAddress());
        i.putExtra("latitude", gp.getLatitude());
        i.putExtra("longitude", gp.getLongitude());
        this.startActivity(i);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {

            // display a floating message
            Toast.makeText(this,
                    inputSearch.getText(), Toast.LENGTH_LONG).show();

            adapter.sortByAddress(inputSearch.getText().toString());
            adapter.notifyDataSetChanged();

            return true;

        }

        return false;
    }
}