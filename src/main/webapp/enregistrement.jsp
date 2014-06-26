<html>
<head>
<title>Evaluator-8000 : Mes processeurs ont besoin de votre savoir</title>
<link rel=stylesheet type="text/css" href="style.css" />
</head>
<header>
<jsp:include page="header.jsp" />
</header>
<nav>
<jsp:include page="nav.jsp" />
</nav>
<section>
<h3>Evaluator-8000 réclame que vous vous enregistriez</h3>
<form method="post" action="/evaluator8000/register">
		<label>Identifiant*: </label><input type="text" name="login" required/><br />
		<label>Mot de passe*: </label><input type="password" name="passwd" required/><br />
		<input type="submit" value="S'enregistrer" />
	</form>
</section>
</html>