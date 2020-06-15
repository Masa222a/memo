<?php
    //データベース接続
    $dsn = "mysql:dbname=php_tools;host=localhost;charset=utf8mb4";
    $username = "root";
    $password = "";
    $pdo = new PDO($dsn, $username, $password, $options);

    //追加ボタンが押された時の処理
    if (null != @$_POST["create"]) { //追加ボタンが押され方どうかを確認
        if(@$_POST["title"] != "" OR @$_POST["contents"] != "") { //メモが入力されているかを確認
            //メモの内容を追加するSQL文を作成し、executeで実行
            $stmt = $pdo->prepare("INSERT INTO memo(title,contents) VALUE (:title,:contents)"); //SQL文の骨子を準備
            $stmt->bindvalue(":title", @$_POST["title"]); //titleをpost送信されたtitleの内容に置換
            $stmt->bindvalue(":contents", @$_POST["contents"]); //contentsをpost送信されたcontentsの内容に置換
            $stmt->execute();
        }
    }

    //変更ボタンが押された時の処理
    if (null != @$_POST["update"]) { //変更ボタンが押されたかどうかを確認
        $stmt = $pdo->prepare("UPDATE memo SET title=:title, contents=:contents WHERE ID=:id");
        $stmt->bindvalue(":title", @$_POST["title"]);
        $stmt->bindvalue(":contents", @$_POST["contents"]);
        $stmt->bindvalue(":id", @$_POST["id"]);
        $stmt->execute();

    }

    //削除ボタンが押された時の処理を記述します。
    if (null != @$_POST["delete"]) { //削除ボタンが押されたかどうかを確認
        $stmt = $pdo->prepare("DELETE FROM memo WHERE ID=:id");
        $stmt->bindvalue(":id", @$_POST["id"]);
        $stmt->execute();
    }
?>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Memo</title>
</head>
    <body>
        <!--新規フォーム-->
        新規作成<br>
        <form action="memo.php" method="post">
            Title<br>
            <input type="text" name="title" size="20"></input><br>
            Contents<br>
            <textarea name="contents" style="width:300px; height:100px;"></textarea><br>
            <input type="submit" name="create" value="追加">
        </form>
        <!--以下にメモ一覧を追加-->
        メモ一覧
        <?php
            //memoテーブルからデータを取得
            $stmt = $pdo->query("SELECT * FROM memo");
            //foreachを使ってデータを１つずつ順番に処理していく
            foreach ($stmt as $row):
        ?>
            <form action="memo.php" method="post">
                <input type="hidden" name="id" value="<?php echo $row[0] ?>"></input>
                Title<br>
                <input type="text" name="title" size="20" value="<?php echo $row[1]?>"></input><br>
                Contents<br>
                <textarea name="contents" style="width:300px; height:100px;"><?php echo $row[2]?>></textarea><br>
                <input type="submit" name="update" value="変更">
                <input type="submit" name="delete" value="削除">
            </form>
        <?php endforeach; ?>
    </body>
</html>