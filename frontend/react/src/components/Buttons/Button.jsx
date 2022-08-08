export const Button = ({className, title, onClick}) => {
    return (
    <button
        style={{ fontSize: "12px" }}
        className={`btn ${className}`}
        onClick={onClick}
      >
        {title}
      </button>)
}