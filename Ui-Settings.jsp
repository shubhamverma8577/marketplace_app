<%-- 
    Document   : Ui-Settings
    Created on : 27 May, 2024, 1:19:17 PM
    Author     : ShubhamVerma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>

        <meta charset="utf-8" />
        <title>SweetAlert 2 | Minia - Minimal Admin & Dashboard Template</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="Premium Multipurpose Admin & Dashboard Template" name="description" />
        <meta content="Themesbrand" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="assets/images/favicon.ico">

        <!-- Sweet Alert-->
        <link href="assets/libs/sweetalert2/sweetalert2.min.css" rel="stylesheet" type="text/css" />

        <!-- preloader css -->
        <link rel="stylesheet" href="assets/css/preloader.min.css" type="text/css" />

        <!-- Bootstrap Css -->
        <link href="assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>

    </head>

    <body>

        <!-- <body data-layout="horizontal"> -->

        <!-- Begin page -->
        <div id="layout-wrapper">

            <jsp:include page="Header.jsp" />
            <!-- ========== Left Sidebar Start ========== -->
            <div class="vertical-menu">
                <div data-simplebar class="h-100">
                    <!--- Sidemenu -->
                    <jsp:include page="sidemenu.jsp" />
                    <!-- Sidebar -->
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- Start right Content here -->
            <!-- ============================================================== -->
            <div class="main-content">

                <div class="page-content">
                    <div class="container-fluid">

                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                                    <h4 class="mb-sm-0 font-size-18">Customize App</h4>

                                    <div class="page-title-right">
                                        <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="app-email">previous</a></li>
                                            <li class="breadcrumb-item active">home</li>
                                        </ol>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <!-- end page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h4 class="card-title">Design App</h4>
                                        <p class="card-title-desc">Here is upload banner and logo and Customize your App</p>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-nowrap align-middle justify-content-center mb-0">
                                                <thead>
                                                    <tr>
                                                        <th scope="col" style="width: 50%;">
                                                            Upload Items
                                                        </th>
                                                        <th scope="col" class="text-center">
                                                            Action
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <tr>
                                                        <th scope="row">
                                                            Upload Logo 
                                                        </th>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
                                                                Upload it
                                                            </button>
                                                            <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
                                                                <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h5 class="modal-title" id="staticBackdropLabel2">Upload logo</h5>
                                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <div>
                                                                                <div class="form-group">
                                                                                    <input type="file" class="form-control-file" id="logo">
                                                                                </div>
                                                                            </div> 
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn btn-light" id="uploadButton" data-bs-dismiss="modal">Upload</button>
                                                                            <button type="button" class="btn btn-primary">close</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>

                                                    <tr id="logoRow" style="background-color: honeydew;">
                                                        <th scope="row">
                                                            <span id="logoContainer"></span>
                                                        </th>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-success w-md" id="delet_logo">Delete logo</button>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <th scope="row">
                                                            Upload Banner
                                                        </th>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                                                                Upload it
                                                            </button>
                                                            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                                <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h5 class="modal-title" id="staticBackdropLabel">Upload Banner</h5>
                                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <div>
                                                                                <div class="form-group">
                                                                                    <input type="file" class="form-control-file" id="banner">
                                                                                </div>
                                                                            </div> 
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn btn-light" id="uploadBanner" data-bs-dismiss="modal">Upload</button>
                                                                            <button type="button" class="btn btn-primary">close</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <th scope="row">
                                                            Ajax request example
                                                        </th>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-primary btn-sm waves-effect waves-light" id="ajax-alert">Click me</button>
                                                        </td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                            <!-- end table -->
                                        </div>
                                        <!-- end table responsive -->

                                    </div>
                                </div>
                            </div> <!-- end col -->
                        </div> <!-- end row -->
                    </div> <!-- container-fluid -->
                </div>
                <!-- End Page-content -->


                <footer class="footer">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-sm-6">
                                <script>document.write(new Date().getFullYear())</script> © Minia.
                            </div>
                            <div class="col-sm-6">
                                <div class="text-sm-end d-none d-sm-block">
                                    Design & Develop by <a href="#!" class="text-decoration-underline">Themesbrand</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
            <!-- end main content-->

        </div>
        <!-- END layout-wrapper -->


        <!-- Right Sidebar -->
        <div class="right-bar">
            <div data-simplebar class="h-100">
                <div class="rightbar-title d-flex align-items-center p-3">

                    <h5 class="m-0 me-2">Theme Customizer</h5>

                    <a href="javascript:void(0);" class="right-bar-toggle ms-auto">
                        <i class="mdi mdi-close noti-icon"></i>
                    </a>
                </div>

                <!-- Settings -->
                <hr class="m-0" />

                <div class="p-4">
                    <h6 class="mb-3">Layout</h6>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout"
                               id="layout-vertical" value="vertical">
                        <label class="form-check-label" for="layout-vertical">Vertical</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout"
                               id="layout-horizontal" value="horizontal">
                        <label class="form-check-label" for="layout-horizontal">Horizontal</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2">Layout Mode</h6>

                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-mode"
                               id="layout-mode-light" value="light">
                        <label class="form-check-label" for="layout-mode-light">Light</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-mode"
                               id="layout-mode-dark" value="dark">
                        <label class="form-check-label" for="layout-mode-dark">Dark</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2">Layout Width</h6>

                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-width"
                               id="layout-width-fuild" value="fuild" onchange="document.body.setAttribute('data-layout-size', 'fluid')">
                        <label class="form-check-label" for="layout-width-fuild">Fluid</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-width"
                               id="layout-width-boxed" value="boxed" onchange="document.body.setAttribute('data-layout-size', 'boxed')">
                        <label class="form-check-label" for="layout-width-boxed">Boxed</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2">Layout Position</h6>

                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-position"
                               id="layout-position-fixed" value="fixed" onchange="document.body.setAttribute('data-layout-scrollable', 'false')">
                        <label class="form-check-label" for="layout-position-fixed">Fixed</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-position"
                               id="layout-position-scrollable" value="scrollable" onchange="document.body.setAttribute('data-layout-scrollable', 'true')">
                        <label class="form-check-label" for="layout-position-scrollable">Scrollable</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2">Topbar Color</h6>

                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="topbar-color"
                               id="topbar-color-light" value="light" onchange="document.body.setAttribute('data-topbar', 'light')">
                        <label class="form-check-label" for="topbar-color-light">Light</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="topbar-color"
                               id="topbar-color-dark" value="dark" onchange="document.body.setAttribute('data-topbar', 'dark')">
                        <label class="form-check-label" for="topbar-color-dark">Dark</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2 sidebar-setting">Sidebar Size</h6>

                    <div class="form-check sidebar-setting">
                        <input class="form-check-input" type="radio" name="sidebar-size"
                               id="sidebar-size-default" value="default" onchange="document.body.setAttribute('data-sidebar-size', 'lg')">
                        <label class="form-check-label" for="sidebar-size-default">Default</label>
                    </div>
                    <div class="form-check sidebar-setting">
                        <input class="form-check-input" type="radio" name="sidebar-size"
                               id="sidebar-size-compact" value="compact" onchange="document.body.setAttribute('data-sidebar-size', 'md')">
                        <label class="form-check-label" for="sidebar-size-compact">Compact</label>
                    </div>
                    <div class="form-check sidebar-setting">
                        <input class="form-check-input" type="radio" name="sidebar-size"
                               id="sidebar-size-small" value="small" onchange="document.body.setAttribute('data-sidebar-size', 'sm')">
                        <label class="form-check-label" for="sidebar-size-small">Small (Icon View)</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2 sidebar-setting">Sidebar Color</h6>

                    <div class="form-check sidebar-setting">
                        <input class="form-check-input" type="radio" name="sidebar-color"
                               id="sidebar-color-light" value="light" onchange="document.body.setAttribute('data-sidebar', 'light')">
                        <label class="form-check-label" for="sidebar-color-light">Light</label>
                    </div>
                    <div class="form-check sidebar-setting">
                        <input class="form-check-input" type="radio" name="sidebar-color"
                               id="sidebar-color-dark" value="dark" onchange="document.body.setAttribute('data-sidebar', 'dark')">
                        <label class="form-check-label" for="sidebar-color-dark">Dark</label>
                    </div>
                    <div class="form-check sidebar-setting">
                        <input class="form-check-input" type="radio" name="sidebar-color"
                               id="sidebar-color-brand" value="brand" onchange="document.body.setAttribute('data-sidebar', 'brand')">
                        <label class="form-check-label" for="sidebar-color-brand">Brand</label>
                    </div>

                    <h6 class="mt-4 mb-3 pt-2">Direction</h6>

                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-direction"
                               id="layout-direction-ltr" value="ltr">
                        <label class="form-check-label" for="layout-direction-ltr">LTR</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="layout-direction"
                               id="layout-direction-rtl" value="rtl">
                        <label class="form-check-label" for="layout-direction-rtl">RTL</label>
                    </div>

                </div>

            </div> <!-- end slimscroll-menu-->
        </div>
        <!-- /Right-bar -->

        <!-- Right bar overlay-->
        <div class="rightbar-overlay"></div>

        <!-- JAVASCRIPT -->
        <script src="assets/libs/jquery/jquery.min.js"></script>
        <script src="assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="assets/libs/simplebar/simplebar.min.js"></script>
        <script src="assets/libs/node-waves/waves.min.js"></script>
        <script src="assets/libs/feather-icons/feather.min.js"></script>
        <!-- pace js -->
        <script src="assets/libs/pace-js/pace.min.js"></script>

        <!-- Sweet Alerts js -->
        <script src="assets/libs/sweetalert2/sweetalert2.min.js"></script>

        <!-- Sweet alert init js-->
        <script src="assets/js/pages/sweetalert.init.js"></script>

        <script src="assets/js/app.js"></script>
        <script type="text/javascript">
            <%
                String userloginid = (String) request.getSession().getAttribute("user_login_id");
                String email = (String) request.getSession().getAttribute("email");
                String company_name = (String) request.getSession().getAttribute("company_name");
            %>
                                   var userloginid = "<%= userloginid%>";
                                   console.log("userloginid: " + userloginid);

                                   $(document).ready(function () {
                                       $("#uploadButton").click(function () {
                                           var file = $("#logo")[0].files[0];
                                           var reader = new FileReader();

                                           reader.onloadend = function () {
                                               var base64Image = reader.result.split(',')[1]; // Get the Base64 part

                                               $.ajax({
                                                   type: "POST",
                                                   url: "uploadlogo", // Replace with your controller mapping
                                                   data: JSON.stringify({
                                                       imageFile: base64Image,
                                                       userloginid: userloginid
                                                   }),
                                                   contentType: "application/json; charset=utf-8",
                                                   success: function (response) {
                                                       console.log("Image uploaded successfully");
                                                       console.log(response);
                                                       location.reload();
                                                   },
                                                   error: function (xhr, status, error) {
                                                       console.error("Error uploading image:", status, error);
                                                   }
                                               });
                                           }

                                           if (file) {
                                               reader.readAsDataURL(file); // Convert the file to Base64
                                           }
                                       });
                                   });
                                   $(document).ready(function () {
                                       var userloginid = "<%= userloginid%>";
                                       console.log("userloginid: " + userloginid);

                                       // Function to send userloginid when the page loads
                                       function getUserLogo() {
                                           $.ajax({
                                               type: "POST",
                                               url: "getuploadlogo",
                                               data: {userloginid: userloginid}, // sending data as application/x-www-form-urlencoded
                                               success: function (response) {
                                                   console.log("UserLoginID sent successfully");
                                                   console.log(response);

                                                   var jsonResponse = JSON.parse(response);
                                                   if (jsonResponse.length > 0) {
                                                       var bannerId = jsonResponse[0].banner_id;
                                                       var bannerVarify = jsonResponse[0].banner_varify;

                                                       var bannerIdElement = document.createElement("input");
                                                       bannerIdElement.type = "hidden";
                                                       bannerIdElement.id = "bannerId";
                                                       bannerIdElement.value = bannerId;
                                                       document.body.appendChild(bannerIdElement);

                                                       var bannerVarifyElement = document.createElement("input");
                                                       bannerVarifyElement.type = "hidden";
                                                       bannerVarifyElement.id = "bannerVarify"; // Use a different ID
                                                       bannerVarifyElement.value = bannerVarify;
                                                       document.body.appendChild(bannerVarifyElement);

                                                       if (jsonResponse[0].logo) {
                                                           var logoData = jsonResponse[0].logo;
                                                           // Create image element
                                                           var imgElement = document.createElement("img");
                                                           imgElement.src = "data:image/png;base64," + logoData; // Adjust the mime type accordingly
                                                           imgElement.style.width = "20%";
                                                           document.getElementById("logoContainer").appendChild(imgElement);
                                                       } else {
                                                           // Hide the row if there is no logo data
                                                           document.getElementById("logoRow").style.display = "none";
                                                       }
                                                   }
                                                   console.log("bannerId ---> " + bannerId + ", bannerVarify ---> " + bannerVarify);
                                               },
                                               error: function (xhr, status, error) {
                                                   console.error("Error sending UserLoginID:", status, error);
                                               }
                                           });
                                       }

                                       // Call the function on page load
                                       getUserLogo();
                                   });


                                   $(document).ready(function () {
                                       var userloginid = "<%= userloginid%>";
                                       console.log("userloginid: " + userloginid);

                                       $("#delet_logo").click(function () {
                                           $.ajax({
                                               type: "POST",
                                               url: "userdeletelogo", // Replace with your controller mapping
                                               data: {userloginid: userloginid}, // Sending data as form data
                                               contentType: "application/x-www-form-urlencoded; charset=UTF-8", // Explicitly set content type
                                               success: function (response) {
                                                   alert("Image deleted successfully");
                                                   console.log(response);
                                                   location.reload();
                                               },
                                               error: function (xhr, status, error) {
                                                   console.error("Error deleting image:", status, error);
                                               }
                                           });
                                       });
                                   });

                                   var userloginid = "<%= userloginid%>";
                                   var email = "<%= email%>";
                                   var company_name = "<%= company_name%>";
                                   console.log("userloginid: " + userloginid + " email: " + email + " company_name: " + company_name);

                                   $(document).ready(function () {
                                       $("#uploadBanner").click(function () {
                                           var bannerVarify = $("#bannerVarify").val();
                                           var bannerId = $("#bannerId").val();
                                           var file = $("#banner")[0].files[0];
                                           var reader = new FileReader();

                                           reader.onloadend = function () {
                                               var base64Image = reader.result.split(',')[1]; // Get the Base64 part

                                               $.ajax({
                                                   type: "POST",
                                                   url: "uploadBanners", // Replace with your controller mapping
                                                   data: JSON.stringify({
                                                       userloginid: userloginid,
                                                       base64Image: base64Image,
                                                       email: email,
                                                       company_name: company_name,
                                                       bannerId: bannerId,
                                                       bannerVarify:bannerVarify
                                                   }),
                                                   contentType: "application/json; charset=utf-8",
                                                   success: function (response) {
                                                       console.log("Image uploaded successfully");
                                                       console.log(response);
                                                       location.reload();
                                                   },
                                                   error: function (xhr, status, error) {
                                                       console.error("Error uploading image:", status, error);
                                                   }
                                               });
                                           }

                                           if (file) {
                                               reader.readAsDataURL(file); // Convert the file to Base64
                                           }
                                       });
                                   });


        </script>
    </body>

</html>

