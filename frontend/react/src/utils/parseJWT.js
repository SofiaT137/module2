export const parseJWT = (token) => {
  let payload = token.split(".")[1];
  let decoded = atob(payload);
  let token1 = JSON.parse(decoded);
  return token1;  
  }