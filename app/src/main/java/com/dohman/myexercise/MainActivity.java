package com.dohman.myexercise;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String base_URL = "https://api.github.com";
    private GridView gridView;
    private SearchView searchView;

    private ArrayList<User> userList = new ArrayList<User>();
    private UserBaseAdapter userBaseAdapter;
    private ProgressDialog progressBar;
    private Context context;
    private Disposable disposable;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        disposable = new Disposable() {
            @Override
            public void dispose() {

            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(this);

        Observable.merge(getObservables())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    public List<Observable<User>> getObservables() {
        List<Observable<User>> users = Arrays.asList(
                getObservable("mojombo"),
                getObservable("defunkt"),
                getObservable("pjhyett"),
                getObservable("ezmobius"),
                getObservable("ivey"));
        return users;
    }

    public Observable<User> getObservable(String login) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        NetWorkInterface api = retrofit.create(NetWorkInterface.class);

        return api.getUser(login);

    }

    public Observer<User> getObserver() {
        return new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                //startProgressBar();
            }

            @Override
            public void onNext(User user) {
                Log.d(TAG, "User in Observer is: " + user.getLogin());

                if (user.getPublic_repos() > 50) {
                    userList.add(user);
                }
                //userBaseAdapter.notifyDataSetChanged();
                //updatedProgressBar()

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
                userBaseAdapter = new UserBaseAdapter(MainActivity.this, R.layout.user_list, userList);
                gridView.setAdapter(userBaseAdapter);
                //progressBar.dismiss();
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, UserDetailActivity.class);
        User user = (User) parent.getItemAtPosition(position);
        intent.putExtra("USER_DETAILS", user);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);


        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);

        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                final String new_text = newText;

                Observable<User> searchResultObservable =
                        getObservable(newText);
                searchResultObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getObserver());

                return true;
            }
        });

        return true;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));


                }
            }
        }
    }
}
