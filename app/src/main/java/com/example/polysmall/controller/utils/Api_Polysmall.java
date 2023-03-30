package com.example.polysmall.controller.utils;

import com.example.polysmall.controller.models.model.DanhsachModel;
import com.example.polysmall.controller.models.model.LichsuModel;
import com.example.polysmall.controller.models.model.MessageModel;
import com.example.polysmall.controller.models.model.SanphamModel;
import com.example.polysmall.controller.models.model.ThanhtoanModel;
import com.example.polysmall.controller.models.model.ThongkeModel;
import com.example.polysmall.controller.models.model.UserModel;
import com.example.polysmall.controller.models.model.DanhmucModel;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api_Polysmall {
    // GET
    String GET_danhmuc = "Get_category.php";
    String GET_product = "Get_product.php";
    String GET_Quanlysanpham = "Get_Quanlysanpham.php";
    String GET_product_timkiem = "Get_product_timkiem.php";
    String GET_noibat = "Get_noibat.php";
    String Get_Moinhat = "GetAll_product.php";
    String Get_Cunhat = "GetAll_product_cunhat.php";
    String GET_sanphamtheo_id = "Get_product_id.php";
    String GET_sapxeptangdan = "Sapxep_tangdan.php";
    String GET_sapxepgiamdan = "Sapxep_giamdan.php";
    String GET_user = "Get_user.php";
    String GET_thongkenhieunhat ="TK_TopsanphammuaNHIEU.php";
    String GET_danhsach_thongkenhieunhat ="TK_DS_Topnhieunhat.php";
    String GET_thongkeitnhat ="TK_TopsanphammuaIT.php";
    String GET_danhsachsanphambanraitnhat ="TK_DS_Topmuaitnhat.php";
    String Doanhthu ="Tk_Tongtien.php";
    String SUMTONGTIEN ="TK_sumall.php";
    String SUMVIP ="TK_tongtienVIP.php";
    String GETdanhsachthongketong ="TKDanhsachAllDoanhthu.php";
    // trạng thái lịch sử
    String Trangthai0 = "Trangthai0.php";
    String Trangthai1 = "Trangthai1.php";
    String Trangthai2 = "Trangthai2.php";
    String Trangthai3 = "Trangthai3.php";
    String Trangthai4 = "Trangthai4.php";
    String POST_Lichsu = "Lichsu.php";
    //thong ke trang thai lich su// cout
    String thongketrangthai0 = "TK_count_Trangthai0.php";
    String thongketrangthai1 = "TK_count_Trangthai1.php";
    String thongketrangthai2 = "TK_count_Trangthai2.php";
    String thongketrangthai3 = "TK_count_Trangthai3.php";
    String thongketrangthai4 = "TK_count_Trangthai4.php";
    //thong ke trang thai tong sum
    String thongketongtientrangthai0 = "Tk_Tongtien_Trangthai1.php";
    String thongketongtientrangthai1 = "Tk_Tongtien_Trangthai2.php";
    String thongketongtientrangthai2 = "Tk_Tongtien_Trangthai3.php";
    String thongketongtientrangthai3 = "Tk_Tongtien_Trangthai4.php";
    String thongketongtientrangthai4 = "Tk_Tongtien_Trangthai5.php";
    String POST_dangky ="P_Register.php";
    String POST_dangnhap = "P_login.php";
    String POST_timkiem = "T_timkiem.php";
    String POST_thanhtoan = "Thanhtoan.php";
    String POST_THEOID = "Get_Danhsachtheo_id.php";
    String POST_Sanpham = "Post_product.php";
    String UPDATE_sanpham = "U_Update_product.php";
    String TRUYVAN = "P_truyvan.php";
    String Mokhoataikhoan = "MokhoaUser.php";
    String Huybovohieuhoa = "U_vohieuhoa0.php";
    String Vohieuhoa = "U_vohieuhoa1.php";
    String Muaquangcao = "Muaquangcao.php";
    String POST_Queqnmatkhau= "quenmatkhau.php";
    String PostTrangthaidonhang= "U_donhangAdmin.php";
    String POSR_HUYDONMUA= "HuyDonmua.php";
    //UPLOADFILE
    String Upload_file_sanpham = "U_Update_hinhanh.php";
    String UpdatePassword = "U_Update_Password.php";
    String UpdateProfile = "U_Update_ProfileUser.php";
    String Upload_file_user = "U_Load_FILE_user.php";
    // DELETE
    String DELETE_product = "D_delete_product.php";
    String SENDOTP = "SenOTP.php";

//    GET
    @GET(GET_danhmuc)
    Observable<DanhmucModel> GET_DANHMUC();
    @GET(GET_product)
    Observable<SanphamModel> GET_PRODUCT();

    @GET(GET_Quanlysanpham)
    Observable<SanphamModel> GET_QUANLYSANPHAM();
    @GET(GET_product_timkiem)
    Observable<SanphamModel> GET_PRODUCT_TIMKIEM();
    @GET(GET_noibat)
    Observable<SanphamModel> GET_NOIBAT();
    @GET(Get_Moinhat)
    Observable<SanphamModel> GET_MOINHAT();

    @GET(Get_Cunhat)
    Observable<SanphamModel> GET_CUNHAT();

    @GET(GET_sapxeptangdan)
    Observable<SanphamModel> GET_SAPXEP_TANGDAN();

    @GET(GET_sapxepgiamdan)
    Observable<SanphamModel> GET_SAPXEP_GIAMDAN();

    @GET(GET_user)
    Observable<UserModel> GET_KHACHHANG();

    @GET(GET_thongkenhieunhat)
    Observable<ThongkeModel> GET_THONGKE_NHIEUNHAT();

    @GET(GET_danhsach_thongkenhieunhat)
    Observable<DanhsachModel> GET_DANHSACH_NHIEUNHAT();

    @GET(GET_thongkeitnhat)
    Observable<ThongkeModel> GET_THONGKE_ITNHAT();

    @GET(GET_danhsachsanphambanraitnhat)
    Observable<DanhsachModel> GET_DANHSACH_ITNHAT();

    @GET(Doanhthu)
    Observable<ThongkeModel> Doanhthu();
    @GET(SUMTONGTIEN)
    Observable<ThongkeModel> SUMTONGTIENDOANHTHU();
    @GET(SUMVIP)
    Observable<ThongkeModel> SUMVIP();
    ///



//    1
    @GET(thongketrangthai0)
    Observable<ThongkeModel> THONGKE_COUNT_TRANGTHAI0();
    @GET(thongketrangthai1)
    Observable<ThongkeModel> THONGKE_COUNT_TRANGTHAI1();
    @GET(thongketrangthai2)
    Observable<ThongkeModel> THONGKE_COUNT_TRANGTHAI2();
    @GET(thongketrangthai3)
    Observable<ThongkeModel> THONGKE_COUNT_TRANGTHAI3();
    @GET(thongketrangthai4)
    Observable<ThongkeModel> THONGKE_COUNT_TRANGTHAI4();

    //    //thong ke trang thai tong sum

    @GET(thongketongtientrangthai0)
    Observable<ThongkeModel> THONGKE_SUM_TRANGTHAI0();
    @GET(thongketongtientrangthai1)
    Observable<ThongkeModel> THONGKE_SUM_TRANGTHAI1();
    @GET(thongketongtientrangthai2)
    Observable<ThongkeModel> THONGKE_SUM_TRANGTHAI2();
    @GET(thongketongtientrangthai3)
    Observable<ThongkeModel> THONGKE_SUM_TRANGTHAI3();
    @GET(thongketongtientrangthai4)
    Observable<ThongkeModel> THONGKE_SUM_TRANGTHAI4();

    @GET(GETdanhsachthongketong)
    Observable<DanhsachModel> GETDANHSACHTHONGKETONG();
//    POST
    @POST(POST_dangky)
    @FormUrlEncoded
    Observable<UserModel> DANGKY(
            @Field("ho") String ho,
            @Field("ten") String ten,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("diachi") String diachi,
            @Field("password") String password
    );
    @POST(POST_dangnhap)
    @FormUrlEncoded
    Observable<UserModel> DANGNHAP(
            @Field("email") String email,
            @Field("password") String password
    );
    // quen mat khau
    @POST(POST_Queqnmatkhau)
    @FormUrlEncoded
    Observable<UserModel> QUENMATKHAU(
            @Field("email") String email);

    @POST(SENDOTP)
    @FormUrlEncoded
    Observable<UserModel> SENDOTP(
            @Field("mobile") String mobile);

    @POST(TRUYVAN)
    @FormUrlEncoded
    Observable<UserModel> TRUYVANTAIKHOAN(
            @Field("mobile") String mobile,
            @Field("email") String email
    );

    @POST(Mokhoataikhoan)
    @FormUrlEncoded
    Observable<UserModel> MOKHOATAIKHOAN(
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("id") int id
    );

    @POST(Huybovohieuhoa)
    @FormUrlEncoded
    Observable<UserModel> HUYBOVOHIEUHOA(
            @Field("id") int id
    );

    @POST(Vohieuhoa)
    @FormUrlEncoded
    Observable<UserModel> VOHIEUHOA(
            @Field("id") int id
    );

    @POST(Muaquangcao)
    @FormUrlEncoded
    Observable<UserModel> MUAQUANGCAO(
            @Field("id") int id
    );

    @POST(POST_timkiem)
    @FormUrlEncoded
    Observable<SanphamModel> POST_TIMKIEM(
            @Field("search") String search
    );
    @POST(GET_sanphamtheo_id)
    @FormUrlEncoded
    Observable<SanphamModel> GET_SANPHAM_ID(
            @Field("page") int page,
            @Field("id_category") int id_category
    );

    // thanh toán đơn hàng
    @POST(POST_thanhtoan)
    @FormUrlEncoded
    Observable<MessageModel> POST_THANHTOAN(
            @Field("ho") String ho,
            @Field("ten") String ten,
            @Field("email") String email,
            @Field("mobile") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );
    // them sua _ san pham moi
      @POST(POST_Sanpham)
    @FormUrlEncoded
    Observable<MessageModel> ADD_SANPHAM(
              @Field("name_product") String name_product,
              @Field("price_product") String price_product,
              @Field("imageview_product") String imageview_product,
              @Field("describe_product") String describe_product,
              @Field("id_category") int id_category
    );
    // UDATAE
      @POST(UPDATE_sanpham)
    @FormUrlEncoded
    Observable<MessageModel> UPDATE_SANPHAM(
            @Field("name_product") String name_product,
            @Field("price_product") String price_product,
            @Field("imageview_product") String imageview_product,
            @Field("describe_product") String describe_product,
            @Field("id_category") int id_category,
            @Field("id") int id
    );


    @POST(UpdateProfile)
    @FormUrlEncoded
    Observable<MessageModel> UPDATEPROFILE(
            @Field("ho") String ho,
            @Field("ten") String ten,
            @Field("diachi") String diachi,
            @Field("mobile") String mobile,
            @Field("image_user") String image_user,
            @Field("id") int id
    );

    @Multipart
    @POST(Upload_file_sanpham)
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);

    @Multipart
    @POST(Upload_file_user)
    Call<MessageModel> UPLOAD_FILE_USER(@Part MultipartBody.Part file);

    // xoa san pham

    @POST(POST_Lichsu)
    @FormUrlEncoded
    Observable<LichsuModel> POST_LICHSU_DONHANG(
            @Field("iduser") int id
    );
    // trạng thái lịch sử
    @POST(Trangthai0)
    @FormUrlEncoded
    Observable<LichsuModel> TRANGTHAI0(
            @Field("iduser") int id
    );
    @POST(Trangthai1)
    @FormUrlEncoded
    Observable<LichsuModel> TRANGTHAI1(
            @Field("iduser") int id
    );
    @POST(Trangthai2)
    @FormUrlEncoded
    Observable<LichsuModel> TRANGTHAI2(
            @Field("iduser") int id
    );
    @POST(Trangthai3)
    @FormUrlEncoded
    Observable<LichsuModel> TRANGTHAI3(
            @Field("iduser") int id
    );
    @POST(Trangthai4)
    @FormUrlEncoded
    Observable<LichsuModel> TRANGTHAI4(
            @Field("iduser") int id
    );
    @POST(POST_THEOID)
    @FormUrlEncoded
    Observable<SanphamModel> POST_THEO_ID(
            @Field("id_category") int id_category
    );

    @POST(PostTrangthaidonhang)
    @FormUrlEncoded
    Observable<MessageModel> UPDATE_TRANGTHAI_ADMIN(
            @Field("id") int id,
            @Field("trangthai") int trangthai);

    @POST(POSR_HUYDONMUA)
    @FormUrlEncoded
    Observable<MessageModel> HUYDONMUA(
            @Field("id") int id
    );
    // DELETE

    @POST(DELETE_product)
    @FormUrlEncoded
    Observable<MessageModel> DELETE_PRODUCT(
            @Field("id") int id
    );

    @POST(UpdatePassword)
    @FormUrlEncoded
    Observable<UserModel> DOIMATKHAU(
            @Field("password") String password,
            @Field("id") int id
    );
}
