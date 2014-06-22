<html>
<body>
	<h2>Ecran de test</h2>
	<h3>Trouver média à partir de son titre (et de sa plateforme pour les jeux)</h3>
	<form method="post" action="/evaluator8000/findMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" /><br />
		<label>Platform*: </label><input type="text" name="platform" /><br />
		<label>Media-type*: </label><select name="media" size="1"><option>game</option><option>movie</option></select><br />
		<input type="submit" value="Trouver" />
	</form>
	<h3>Chercher les médias ayant le titre suivant</h3>
	<form method="post" action="/evaluator8000/searchMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" /><br />
		<label>Media-type*: </label><select name="media" size="1"><option>game</option><option>movie</option></select><br />
		<input type="submit" value="Rechercher" />
	</form>
</body>
</html>