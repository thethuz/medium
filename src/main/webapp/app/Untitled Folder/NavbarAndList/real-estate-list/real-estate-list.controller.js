(function() {
    'use strict';

    angular
        .module('giasanApp')
        .controller('RealEstateListController', RealEstateListController);

    RealEstateListController.$inject = ['$scope', '$state'];

    function RealEstateListController ($scope, $state) {
        var vm = this;

        $scope.products = [
        	{
        		name: 'SN2007 Khu đô thị TimeCity',
                address: 'Mai Động, Hai Bà Trưng, Hà Nội',
        		area: '90m2',
        		image: 'content/images/chungcu.jpg',
        		room: '3 phòng (1 khách, 2 ngủ)',
                host:'Tập đoàn Vingroup',
                progress: 'Đang xây dựng',
        		price: '2.5 tỉ'
        	},
        	{
        		name: 'SN1001 Khu đô thị Mỹ Đình',
                address: 'Mỹ Đình, Cầu Giấy , Hà Nội',
        		area: '150m2',
        		image: 'content/images/flc.jpg',
        		room: '4 phòng (2 khách, 2 ngủ)',
                host:'Tập đoàn FLC',
                progress: 'Đã hoàn thành',
        		price: '3.5 tỉ'
        	},
        	{
        		name: 'SN990 Khu đô thị RoyalCity',
                address: 'Ngã Tư Sở, Đống Đa, Hà Nội',
        		area: '200m2',
        		image: 'content/images/royal.jpg',
        		room: '5 phòng (2 khách, 2 ngủ, 1 bếp)',
                host:'Tập đoàn Vingroup',
                progress: 'Đã hoàn thành',
        		price: '5 tỉ'
        	},
            {
                name: 'SN2007 Khu đô thị TimeCity',
                address: 'Mai Động, Hai Bà Trưng, Hà Nội',
                area: '90m2',
                image: 'content/images/chungcu.jpg',
                room: '3 phòng (1 khách, 2 ngủ)',
                host:'Tập đoàn Vingroup',
                progress: 'Đang xây dựng',
                price: '2.5 tỉ'
            },
            {
                name: 'SN1001 Khu đô thị Mỹ Đình',
                address: 'Mỹ Đình, Cầu Giấy , Hà Nội',
                area: '150m2',
                image: 'content/images/flc.jpg',
                room: '4 phòng (2 khách, 2 ngủ)',
                host:'Tập đoàn FLC',
                progress: 'Đã hoàn thành',
                price: '3.5 tỉ'
            },
            {
                name: 'SN990 Khu đô thị RoyalCity',
                address: 'Ngã Tư Sở, Đống Đa, Hà Nội',
                area: '200m2',
                image: 'content/images/royal.jpg',
                room: '5 phòng (2 khách, 2 ngủ, 1 bếp)',
                host:'Tập đoàn Vingroup',
                progress: 'Đã hoàn thành',
                price: '5 tỉ'
            }
        ];

        $scope.d = [ [1,6.5],[2,6.5],[3,7],[4,8],[5,7.5],[6,7],[7,6.8],[8,7],[9,7.2],[10,7],[11,6.8],[12,7] ];
        
	    
    }
})();
