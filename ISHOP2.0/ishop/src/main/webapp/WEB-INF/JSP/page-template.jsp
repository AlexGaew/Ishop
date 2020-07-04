<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<title>&lt;/&gt;GtwoA.Ishop</title>
<link rel="stylesheet" href="/static/css/bootstrap.css">
<link rel="stylesheet" href="/static/css/bootstrap-theme.css">
<link rel="stylesheet" href="/static/css/font-awesome.min.css">
<link rel="stylesheet" href="/static/css/app.css">
</head>
<body>
	<header>
		<jsp:include page="fragment/header.jsp" />

	</header>
	<div class="container-fluid">
		<div class="row">
			<aside class="col-12 col-sm-4 col-md-3 col-lg-2">
				<jsp:include page="fragment/aside.jsp" />

			</aside>
			<main class="col-12 col-sm-8 col-md-9 col-lg-10 col-xl-10 mainXL">
				<jsp:include page="${currentPage}" />

			</main>
		</div>
	</div>
	<footer class="footer">
		<jsp:include page="fragment/footer.jsp" />

	</footer>
	<script src="/static/js/jQuery.js"></script>
	<script src="/static/js/bootstrap.js"></script>
	<script src="/static/js/app.js"></script>


</body>
</html>