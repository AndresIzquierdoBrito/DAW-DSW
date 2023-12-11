using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace AUT03_04_AndresIzquierdo_JuegosAuthAPI.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Genre",
                columns: table => new
                {
                    IdGenre = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(12)", maxLength: 12, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Genre", x => x.IdGenre);
                });

            migrationBuilder.CreateTable(
                name: "Game",
                columns: table => new
                {
                    GameId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Title = table.Column<string>(type: "nvarchar(12)", maxLength: 12, nullable: false),
                    GenreId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Game", x => x.GameId);
                    table.ForeignKey(
                        name: "FK_Game_Genre_GenreId",
                        column: x => x.GenreId,
                        principalTable: "Genre",
                        principalColumn: "IdGenre",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.InsertData(
                table: "Genre",
                columns: new[] { "IdGenre", "Name" },
                values: new object[] { 1, "Adventure" });

            migrationBuilder.InsertData(
                table: "Genre",
                columns: new[] { "IdGenre", "Name" },
                values: new object[] { 2, "Plataformer" });

            migrationBuilder.InsertData(
                table: "Genre",
                columns: new[] { "IdGenre", "Name" },
                values: new object[] { 3, "Strategy" });

            migrationBuilder.InsertData(
                table: "Game",
                columns: new[] { "GameId", "GenreId", "Title" },
                values: new object[,]
                {
                    { 1, 1, "Last of us" },
                    { 2, 1, "Tomb Raider" },
                    { 3, 2, "Mario Bros" },
                    { 4, 2, "Rayman" },
                    { 5, 3, "Starcraft" }
                });

            migrationBuilder.CreateIndex(
                name: "IX_Game_GenreId",
                table: "Game",
                column: "GenreId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Game");

            migrationBuilder.DropTable(
                name: "Genre");
        }
    }
}
