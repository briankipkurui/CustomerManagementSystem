import {useSelector} from "react-redux";
import {Link} from "react-router-dom"

const ProductsComponents = () => {
    const accounts = useSelector((state) =>state.allAccounts.accounts)
    const RenderList = accounts.map((account) =>{
        const {id,firstName,lastName,email,phoneNumber} = account
        return(
            <section className="container card-height" key={id}>
                <div className="flex-flex">
                    <Link to={`/adminPane/${id}`}>
                     <div className="Card ">
                        <div className="Container">
                            <h4><b>{id} {firstName} {lastName}</b></h4>
                            <h4><b>{phoneNumber}</b></h4>
                            <hp>{email}</hp>
                        </div>
                     </div>
                    </Link>
                </div>
            </section>
        )
    })
  return(
      <>

              {RenderList}
      </>
  )
}
export default ProductsComponents