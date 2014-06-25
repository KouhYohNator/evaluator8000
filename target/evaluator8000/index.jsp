<html>
<body>
	<h2>Ecran de test</h2>
	<h3>Enregister un utilisateur</h3>
	<form method="post" action="/evaluator8000/register">
		<label>Identifiant*: </label><input type="text" name="login" required/><br />
		<label>Mot de passe*: </label><input type="password" name="passwd" required/><br />
		<input type="submit" value="S'enregistrer" />
	</form>
	
	<h3>Trouver média à partir de son titre (et de sa plateforme pour les jeux)</h3>
	<form method="post" action="/evaluator8000/findMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" required/><br />
		<label>Platform*: </label><input type="text" name="platform" /><br />
		<label>Media-type*: </label><select name="media" size="1"><option>game</option><option>movie</option></select><br />
		<input type="submit" value="Trouver" />
	</form>
	
	<h3>Chercher les médias ayant le titre suivant</h3>
	<form method="post" action="/evaluator8000/searchMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" required/><br />
		<label>Media-type*: </label><select name="media" size="1"><option>game</option><option>movie</option></select><br />
		<input type="submit" value="Rechercher" />
	</form>
	
	<h3>Commenter le média ayant le titre suivant avec l'utilisateur suivant</h3>
	<form method="post" action="/evaluator8000/commentMedia">
		<label>Titre*: </label><input type="text" name="title" value="The Elder Scrolls V: Skyrim" required/><br />
		<label>Platforme*: </label><input type="text" name="platform" /><br />
		<label>Utilisateur*: </label><input type="text" name="login" value="Titi" required/><br />
		<label>Note* :</label><input type="number" name="score" min="0" max ="10" step="0.5" required/>
		<label>Commentaire*: </label><textarea name="content" rows="10" cols="50" required></textarea><br />
		<label>Media-type*: </label><select name="media" size="1"><option>game</option><option>movie</option></select><br />
		<input type="submit" value="Poster le commentaire" />
	</form>
</body>
</html>