export const Footer = () => {
  
let login = localStorage.getItem("login");
  return (
    <>
      {login ? (
        <>
          <div className="footerMainPage">
            <h5>2022, © EPAM, Sofiya Tkachenia</h5>
          </div>
        </>
      ) : (
        <div className="footerLoginPage">
          <h5>2022, © EPAM, Sofiya Tkachenia</h5>
        </div>
      )}
    </>
  );
};
