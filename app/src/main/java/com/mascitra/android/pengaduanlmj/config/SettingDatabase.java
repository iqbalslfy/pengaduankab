package com.mascitra.android.pengaduanlmj.config;

import static com.mascitra.android.pengaduanlmj.config.http.URL;

/**
 * Created by SONY on 30/08/2017.
 */

public class SettingDatabase {

    public static final String URL_ADD= URL+"create.php";
    public static final String URL_GET_ALL = URL+"view.php";
    public static final String URL_GET_DETAIL = URL+"vibio.php?no_ktp=";
    public static final String URL_GET_PERDA = URL + "viperda.php?no_ktp=";
    public static final String URL_GET_UPDATE = "http://192.168.1.31/pelayanan/update.php";
    public static final String URL_GET_DELETE = "http://192.168.1.31/pelayanan/delete.php?no_ktp=";

    public static final String KEY_EMP_ID ="id";
    public static final String KEY_EMP_NAMA="nama";
    public static final String KEY_KTP="ktp";
    public static final String KEY_KEC="kecamatan";
    public static final String KEY_KEL="kelurahan";
    public static final String KEY_RT="rt";
    public static final String KEY_RW="rw";
    public static final String KEY_ALAMAT="alamat";
    public static final String KEY_EMAIL="email";
    public static final String KEY_KPD="peng_kpd";
    public static final String KEY_ISI="peng_isi";
    public static final String KEY_WAKTU="waktu";
    public static final String KEY_FOTO="image";
    public static final String KEY_TGL_TGGP="waktu_tanggapan";
    public static final String KEY_TANGGAPAN="tanggapan";

    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID ="id";
    public static final String TAG_NAMA="nama";
    public static final String TAG_KTP="ktp";
    public static final String TAG_KEC="kecamatan";
    public static final String TAG_KEL="kelurahan";
    public static final String TAG_RT="rt";
    public static final String TAG_RW="rw";
    public static final String TAG_ALAMAT="alamat";
    public static final String TAG_EMAIL="email";
    public static final String TAG_KPD="peng_kpd";
    public static final String TAG_ISI="peng_isi";
    public static final String TAG_WAKTU="waktu";
    public static final String TAG_FOTO="image";
    public static final String TAG_TGL_TGGP="waktu_tanggapan";
    public static final String TAG_TANGGAPAN="tanggapan";


    public static final String EMP_ID ="emp_id";
}
