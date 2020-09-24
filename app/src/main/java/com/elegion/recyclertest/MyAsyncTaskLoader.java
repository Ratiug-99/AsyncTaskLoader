package com.elegion.recyclertest;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyAsyncTaskLoader extends AsyncTaskLoader<String> {
    private int mID;

    public MyAsyncTaskLoader(Context context, int ID) {
        super(context);
        mID = ID;
    }

    @Override
    protected void onStopLoading() {
        Log.d("DBG", "onStopLoading: ");
        super.onStopLoading();
    }

    @Override
    public String loadInBackground() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{String.valueOf(mID), String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

        if (cursor != null && cursor.moveToFirst()) {
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();

            //
            return number;
        } else {
            return null;
        }
    }
}
