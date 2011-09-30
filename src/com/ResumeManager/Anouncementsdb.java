package com.ResumeManager;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Anouncementsdb extends SQLiteOpenHelper{

	final static String com_name="company";
	final static String db_name="database";
	final static String announcement_table="announcement_table";
	final static String date="date";
	final static String time="time";
	final static String body="body";
	final static String user="user";
	final static String unique_id="_id";
	final static int DATABASE_VERSION = 2;
	private ArrayList<Announce> announcements;
	private Context ctx;
	public static SQLiteDatabase db;
	
	public static String query = "SELECT _id, company, date, time, body, user FROM announcement_table";
	
	public String CREATE_TABLE_ANNOUNCE = "CREATE TABLE "+ announcement_table + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + com_name + " TEXT NOT NULL," +
	date + " TEXT NOT NULL," + time + " TEXT NOT NULL," + body + " TEXT NOT NULL," + user +" TEXT NOT NULL);";
	
	public static String CREATE_TABLE_RECRUIT = "CREATE TABLE "+ Recruitersdb.recruiters_table + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "

	+ Recruitersdb.grade + " TEXT NOT NULL, "
	+ Recruitersdb.rec_name + " TEXT NOT NULL," 
	+ Recruitersdb.date + " TEXT NOT NULL," 
	+ Recruitersdb.eligibilty + " TEXT NOT NULL," 
	+ Recruitersdb.branches_be +" TEXT, " 
	+ Recruitersdb.branches_me +" TEXT, "
	+ Recruitersdb.branches_intern +" TEXT, "
	+ Recruitersdb.pkg_be +" TEXT, "
	+ Recruitersdb.pkg_me +" TEXT, "
	+ Recruitersdb.pkg_intern +" TEXT, "
	+ Recruitersdb.cutoff_be + " TEXT, " 
	+ Recruitersdb.cutoff_me +" TEXT, "
	+ Recruitersdb.cutoff_intern + " TEXT);";
	

public Anouncementsdb(Context context, ArrayList<Announce> new_announcements){
		super(context, db_name, null, DATABASE_VERSION);
		ctx = context;
		Log.d("Anouncementsdb","consructor");
		announcements = new_announcements;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("Anouncementsdb","in oncreate");
		db.execSQL(CREATE_TABLE_ANNOUNCE);
		db.execSQL(CREATE_TABLE_RECRUIT);
		
//we need to declare all the tables to be used in a database together...because later in onCreate is never
//		called for same database...... 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS announce_table;");
        onCreate(db);		
	}

	public void add_new_to_db(){
		
		Log.d("Anouncementsdb ","addtodb");
		
		db = this.getWritableDatabase();
		Iterator<Announce> Iter = announcements.iterator();
		Announce temp = new Announce();
		long status;
		while(Iter.hasNext()){
			temp = Iter.next();
			ContentValues content = new ContentValues();
//			content.put(unique_id, (Integer.parseInt(temp.id)));
			Log.d("add new to db","the iteraation loop");
			content.put(com_name,temp.com_name);
			content.put(date,temp.date);
			content.put(time,temp.time);
			content.put(body,temp.body);
			content.put(user,temp.user);
			status = db.insert(announcement_table,null,content);
			Log.w((new Long(status)).toString(),"add new to db");
			Log.d("later on stage","the iteraation loop");		
		}
	
	}
	
	public Cursor fetch_all(){
		Log.d("fetch all","announcementdb");
//		return db.query(announcement_table, new String[]{unique_id,com_name,date,time,body,user}, null,null,null,null,unique_id+"DESC");
//		Cursor cursor =  db.query(announcement_table, new String[]{unique_id,com_name,date,time,body,user}, null,null,null,null,null);
		Cursor cursor = db.rawQuery(query, null);
		
//we must have an column called _id in the table if you want to use cursor adapter on the given database and that
//_id should be unique i.e., primary key ...and auto increment 		
//        Log.d(cursor.getString(cursor.getColumnIndex("company")),cursor.getString(cursor.getColumnIndex("date")));
        if(cursor!=null)
        {
        	Log.d("fetch all","not null");
        	cursor.moveToFirst();
        	return cursor;
        }
		return null;
	}
}