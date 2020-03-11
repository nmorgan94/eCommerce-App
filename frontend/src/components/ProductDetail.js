import React, { Component } from "react";
import { API_BASE_URL } from "../constants";
import { observer, inject } from "mobx-react";

@inject("dataStore")
@observer
class ProductDetail extends Component {
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.dataStore.getProductDetail(id);
  }

  handleClick = () => {
    let { id } = this.props.match.params;
    fetch(API_BASE_URL + `/basket/add/${id}`, {
      method: "POST"
    })
      .then(data => {
        console.log("data in basket: " + data.json());
        this.props.dataStore.basket = data;
      })
      .catch(() => console.log("Add to basket api call failed"));

    this.props.history.push(`/basket`);
  };

  render() {
    return (
      <div className="container">
        <div className="">
          <div className="row">
            <div className="col s4">
              <img
                src={this.props.dataStore.productDetail.pictureUrl}
                alt={this.props.dataStore.productDetail.name}
              />
            </div>
            <div className="col s8">
              <div className="row">
                <div className="col s12">
                  {this.props.dataStore.productDetail.name}
                </div>
                {this.props.dataStore.productDetail.price}
                <div className="col s12"></div>
              </div>
            </div>
          </div>
          <button
            className="btn waves-effect waves-teal"
            onClick={this.handleClick}
          >
            Add To Basket
          </button>
        </div>
      </div>
    );
  }
}

export default ProductDetail;
