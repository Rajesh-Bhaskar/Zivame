package com.example.zivame.database

import android.annotation.SuppressLint
import com.example.zivame.model.CartModelClass
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.fragment.app.FragmentActivity


class DatabaseHandler(context: FragmentActivity?): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "CartDatabase"
        private val TABLE_CONTACTS = "CartTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private val KEY_PRICE = "price"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PRICE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addItem(cart: CartModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, cart.userId)
        contentValues.put(KEY_NAME, cart.userName)
        contentValues.put(KEY_PRICE,cart.price)
        contentValues.put(KEY_EMAIL,cart.userEmail)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    @SuppressLint("Range")
    fun viewItemsincart():List<CartModelClass>{
        val cartList:ArrayList<CartModelClass> = ArrayList<CartModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        var price: String
        var image_url: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                price = cursor.getString(cursor.getColumnIndex("price"))
            //    image_url = cursor.getString(cursor.getColumnIndex("image_url"))
                val emp= CartModelClass(userId = userId, userName = userName, userEmail = userEmail, price = price)
                cartList.add(emp)
            } while (cursor.moveToNext())
        }
        return cartList
    }
    //method to update data
    fun updateCart(cart: CartModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, cart.userId)
        contentValues.put(KEY_NAME, cart.userName)
        contentValues.put(KEY_EMAIL,cart.userEmail )

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+cart.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteCart(cart: CartModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, cart.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+cart.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}