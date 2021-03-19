STEPS TO SETUP YOUR LOCAL REPO

    From the terminal:
        -   cd Desktop
            git clone https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0660 PROJECT
        -   Open Android Studio
        -   File -> Open -> Navigate to your Desktop -> PROJECT -> Phase1 -> Choose GameCenter as the folder to open
        -   From the terminal, inside the PROJECT folder you can:
        -   git pull, add, commit, and push to the master

Features:
    Game launch centre:
        The game launch centre uses Firebase login system, allows the users to:
            Sign up/Sign in
                - A user has to use an valid Email address to sign up.
                - The password has to be at least 6 characters
            Check out the user's highest score for each game.    
            Check out the Global Scoreboard(in each game)
                - In the top down order sorted by the scores 
                - Shows user and score by nickname
                - Shows current user's rank and score as a floating text display at the bottom of the screen.
            Choose which game to play(3 games)
                - Sliding Tiles
                - Matching Tiles
                - 2048
            (Bonus)Allows user to remember the last user entry by a checkbox

        Related classes/activities:
            GameLaunchCentreActivity    The activity after login
            LeaderBoardActivity         An option in the GameLaunchCenteR
            LoginSystemActivity         User Login
            RegisterActivity            Registry
            PersonLeaderBoard           The personal scoreboard
            User                        The user class contains the user info
            LeaderBoardActivity         The leaderboard within the games
            CentreAdapter               An adapter for the game centre
            game_centre_item            A class holding the game centre items.
            
   Game 1: Sliding Tiles(All related classes are inside the folder **SlidingTiles**)
        Main Functionaries:
            The game auto loads the last saved state when started.
            When a new game is created this will be the new saved state.
            game complexity:
                Allows user to:
                Choose the complexity of the number of tiles: 3x3, 4x4, 5x5 availiable in Setting/Complexity
                Change the background of the Board to a image, availiable in Setting/Load Image. User need to toggle the 
                    use default background to use numbers(default) or images(an image has to be loaded).
                Choose the number of undo a user can use: 3, 4, 5 times
            Undo: Can undo 3, 4, or 5 times
            Load Game: Allows user to load the last saved state.
            Save Game: Allows user to save current game state.
            Auto Save: The game saves after every more user makes.
            LeaderBoard: Allows the user to check the global leaderboard for this game, and his own rank and highest score.
            
    Game 2: MatchinTiles(All related classes are inside the folder **MatchinTiles**)
    When 2 matching tiles are revealed they vanishes. The game is won when all tiles are gone..
         Main Functionaries:
            The game auto loads the last saved state when started.
            New Game: Allows user to create a fresh new game.When a new game is created this will be the new saved state.
            Auto Save: The game saves after every more user makes.
            LeaderBoard: Allows the user to check the global leaderboard for this game, and his own rank and highest score.                    
            Current Score: Shows the real time current user's score.
            Animations: The game does an animation after the game is won and shows the user's final score.
   
    Game 3: 2048(All related classes are inside the folder **twozerofoureight**)
    The game is lost when no adjacent tile has same numbers. The game is won when 1 tile reaches 2048.
         Main Functionaries:
            The game auto loads the last saved state when started.
            New Game: Allows user to create a fresh new game.When a new game is created this will be the new saved state.
            Auto Save: The game saves after every more user makes.
            Undo: Allows user to undo 3 times max.
            LeaderBoard: Allows the user to check the global leaderboard for this game, and his own rank and highest score.                    
            Current Score: Shows the real time current user's score.
            Animations: Scale animation is trigger if two tiles are conbined or a new tile has been created.