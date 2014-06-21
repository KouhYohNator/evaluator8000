<html>
<body>
	<h2>Ecran de test</h2>
	<h3>Trouver média à partir de son titre et de sa plateforme</h3>
	<form method="post" action="/evaluator/findMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" /><br />
		<label>Platform*: </label><input type="text" name="platform" value="1" /><br />
		<label>Media-type*: </label><input type="text" name="media" value="game" /><br />
		<input type="submit" value="Trouver" />
	</form>
	<h3>Chercher les médias ayant le titre suivant</h3>
	<form method="post" action="/evaluator/searchMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" /><br />
		<label>Media-type*: </label><input type="text" name="media" value="game" /><br />
		<input type="submit" value="Rechercher" />
	</form>
</body>
</html>
