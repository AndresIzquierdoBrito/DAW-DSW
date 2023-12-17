using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using AUT03_06_IzquierdoAndres_AuthMusicaAPI.Models;

namespace AUT03_06_IzquierdoAndres_AuthMusicaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly UserManager<IdentityUser> _userManager;
        private readonly RoleManager<IdentityRole> _roleManager;
        private readonly IConfiguration _configuration;

        /// <summary>
        /// Constructor del controlador de autenticación.
        /// </summary>
        /// <param name="userManager">Gestor de usuarios.</param>
        /// <param name="roleManager">Gestor de roles.</param>
        /// <param name="configuration">Configuración de la aplicación.</param>
        public AuthController(
            UserManager<IdentityUser> userManager,
            RoleManager<IdentityRole> roleManager,
            IConfiguration configuration
        )
        {
            _userManager = userManager;
            _roleManager = roleManager;
            _configuration = configuration;
        }

        /// <summary>
        /// Endpoint para realizar el inicio de sesión de un usuario.
        /// </summary>
        /// <param name="user">DTO que contiene la información del usuario para el inicio de sesión.</param>
        /// <returns>Token JWT si el inicio de sesión es exitoso.</returns>
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] UserDTO user)
        {
            var existingUser = await _userManager.FindByNameAsync(user.UserName);

            // Verifica si el usuario existe
            if (existingUser == null)
                return NotFound();

            // Verifica si la contraseña es correcta
            if (!await _userManager.CheckPasswordAsync(existingUser, user.Password))
                return Unauthorized();

            return Ok(await CreateToken(existingUser)); // Crea el token JWT para el usuario
        }

        /// <summary>
        /// Endpoint para registrar un nuevo usuario.
        /// </summary>
        /// <param name="user">DTO que contiene la información del nuevo usuario.</param>
        /// <returns>Respuesta indicando el resultado del registro.</returns>
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] UserDTO user)
        {
            var userExists = await _userManager.FindByNameAsync(user.UserName); // Busca al usuario en el userManager por nombre

            // Verifica si el usuario existe
            if (userExists != null)
                return StatusCode(StatusCodes.Status500InternalServerError, new { Status = "Error", Message = "User already exists!" });

            IdentityUser newUser = new IdentityUser
            {
                UserName = user.UserName
            };

            // Crea el usuario y en el caso que no sea exitoso, devuelve un error
            var result = await _userManager.CreateAsync(newUser, user.Password);
            if (!result.Succeeded)
                return StatusCode(StatusCodes.Status500InternalServerError, new { Status = "Error", Message = "User creation failed! Please check user details and try again." });

            // Le agrega el rol de User al nuevo usuario creado
            await _userManager.AddToRoleAsync(newUser, "User");


            return Ok(new { Status = "Success", Message = "Usuario creado con éxito." });
        }

        private async Task<string> CreateToken(IdentityUser user)
        {
            var issuedAt = new DateTimeOffset(DateTime.UtcNow).ToUnixTimeSeconds();

            var claims = new List<Claim>
            {
                //Identificador único del token
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                //Fecha de emisión del token
                new Claim(JwtRegisteredClaimNames.Iat, issuedAt.ToString()),
                //Usuario portador del token
                new Claim(JwtRegisteredClaimNames.Sub, user.UserName)
            };
            var userRoles = await _userManager.GetRolesAsync(user);
            foreach (var userRole in userRoles)
                claims.Add(new Claim(ClaimTypes.Role, userRole));

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWT:Key"]));
            var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(
            _configuration["JWT:Issuer"],
            _configuration["JWT:Audience"],
            claims,
            expires: DateTime.UtcNow.AddMinutes(15), // Tiempo de duracion del token
            signingCredentials: credentials);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }
    }
}
