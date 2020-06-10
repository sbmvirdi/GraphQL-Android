package com.geexec.graphql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.geexec.WorkListQuery;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private TextView mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = findViewById(R.id.data);

        ApolloClient client  = ApolloClient.builder().serverUrl("https://api.geexec.com/graphql").build();
        WorkListQuery workList = WorkListQuery.builder().build();
        ApolloCall<WorkListQuery.Data> workListQuery  = client.query(workList);
        long timestart = System.currentTimeMillis();
        workListQuery.enqueue(new ApolloCall.Callback<WorkListQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<WorkListQuery.Data> response) {
                WorkListQuery.Data data = response.getData();

                    long timeend = System.currentTimeMillis();
                    Log.e("Data", data.works().toString());
                    Log.e("Duration:", (timeend - timestart) + " ms");

                    runOnUiThread(() -> {
                        mData.setText(data.works().toString());
                    });



            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
            Log.e("Error",e.getMessage()+"");
            }
        });

    }
}
